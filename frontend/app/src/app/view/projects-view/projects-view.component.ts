import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {IDropdownSettings} from 'ng-multiselect-dropdown';
import {DownloadService} from '../../service/util/download.service';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-projects-info-view',
  templateUrl: './projects-view.component.html',
  styleUrls: ['./projects-view.component.css']
})
export class ProjectsViewComponent implements OnInit {

  constructor(private http: HttpClient, private authService: AuthenticationService, private downloadService: DownloadService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()    
  }

  resourcebundle : any = {};

  area = '';
  repository = '';
  mavenCertified: boolean | undefined;
  isModalEditable = false;
  repositories: string[] = [];
  areas: string[] = [];
  showSaveAlert = false;

  changesMap: Map<string, ProjectInfo> = new Map<string, ProjectInfo>();
  projects: AppProjectInfo[] = [];
  selectedProject: AppProjectInfo = new AppProjectInfo();
  dropdownList: Array<any> = [];
  selectedItems: Array<any> = [];
  dropdownSettings: IDropdownSettings = {};

  ngOnInit(): void {

    this.dropdownList = [
      {item_id: 1, item_text: 'Batch'},
      {item_id: 2, item_text: 'Libreria'},
      {item_id: 3, item_text: 'Online'},
      {item_id: 4, item_text: 'Servizio Web'},
      {item_id: 5, item_text: 'Libreria Client'},
    ];

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: true,
      limitSelection: 5
    };
    this.getProjectsWithoutDependecies();
    console.log(this.projects);
    this.http.get(environment.apiUrl + '/distinctAreas', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.areas.push('area' + response[key]);
      }
    });
    this.http.get(environment.apiUrl + '/repository/kind/Java', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.repositories.push(response[key]);
      }
    });


  }

  private getProjectsWithoutDependecies(): void {
    this.http.get(environment.apiUrl + '/project/withoutDependencies', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      let i = 0;
      for (const key of Object.keys(data)) {
        const projectInfo: ProjectInfo = ProjectInfo.fromObject(response[key]);
        projectInfo.index = i;
        i++;
        const appoProjectInfo: AppProjectInfo = AppProjectInfo.fromProjectInfo(projectInfo);
        this.projects.push(appoProjectInfo);
      }
    });
  }

  getProjectIndexByNameAndRepository(name: string, repository: string): number {
    let index = -1;
    let tmpIndex = 0;

    for (const project of this.projects) {
      if (project.name === name && project.repository === repository) {
        index = tmpIndex;
        break;
      }
      tmpIndex++;
    }
    return index;
  }

  downloadReport(): void {
    const filename = 'ListaProgetti.csv';
    this.downloadService.downloadUtil(filename, 'data_table');
  }


  setSelectedProject(i: any, isModalEditable: boolean): void {
    this.showSaveAlert = this.changesMap.size > 0;
    this.selectedProject = this.projects[i];
    this.selectedItems = this.projects[i].projectType;
    this.isModalEditable = isModalEditable;
  }


  fillChangesList($event: any, name: string, repository: string, isSaveMany: boolean): void {
    if ($event.target === undefined) {
      return;
    }
    const currentProjectIndex = this.getProjectIndexByNameAndRepository(name, repository);
    if (currentProjectIndex !== -1) {
      this.projects[currentProjectIndex].changedValue = true;
    }
    if ($event.target.name.startsWith('mavenCertified')) {
      this.projects[currentProjectIndex].mavenCertified = $event.target.checked;
    }
    if ($event.target.name.startsWith('descrizione')) {
      this.projects[currentProjectIndex].description = $event.target.value;
    }
    if (isSaveMany) {
      this.changesMap.set(name + ':' + repository, this.projects[currentProjectIndex]);
    }
  }


  saveChange(isSaveMany: boolean): void {
    let projectsToSave: ProjectInfo[] = [];

    if (this.changesMap.size > 0 && isSaveMany) {
      this.changesMap.forEach(changedElement => {
          const singleProject: ProjectInfo = ProjectInfo.fromAppProjectInfo(changedElement);
          projectsToSave.push(singleProject);
        }
      );
    } else {
      const currentProjectIndex = this.getProjectIndexByNameAndRepository(this.selectedProject.name, this.selectedProject.repository);
      if (currentProjectIndex !== -1) {
        this.projects[currentProjectIndex].projectType = this.selectedItems;
      }
      projectsToSave.push(ProjectInfo.fromAppProjectInfo(this.projects[currentProjectIndex]));
    }
    this.http.put<any>(environment.apiUrl + '/projectInfo/saveChanges', projectsToSave, this.authService.httpOptions()).toPromise().then(
      ((data) => {
        if (data.response === 'OK') {
          alert('Informazioni inserite con successo!', 'success');
          this.changesMap.clear();
          projectsToSave = [];
          this.projects = [];
          const areaTmp = this.area;
          const repositoryTmp = this.repository;
          const mavenCertifiedTmp = this.mavenCertified;
          this.area = '';
          this.repository = '';
          this.mavenCertified = undefined;
          // tslint:disable-next-line:no-shadowed-variable
          this.getProjectsWithoutDependecies();
          this.area = areaTmp;
          this.repository = repositoryTmp;
          this.mavenCertified = mavenCertifiedTmp;
        } else {
          alert(data.response, 'danger');
        }
      })).catch((err) => {
      alert(err, 'danger');
    });
    const alertPlaceholder: HTMLElement | null = document.getElementById('liveAlertPlaceholder');

    function alert(message: string, type: string): void {
      const wrapper = document.createElement('div');
      wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

      // tslint:disable-next-line:no-non-null-assertion
      alertPlaceholder!.append(wrapper);
    }

  }


  clear(): void {
    this.changesMap.clear();
    this.projects = [];
    this.area = '';
    this.repository = '';
    this.mavenCertified = undefined;
    this.getProjectsWithoutDependecies();

  }

  isAdmin(): boolean {
    return this.authService.getRole() === 'ADMIN';
  }


}

function fillProjectInfo(o: any): ProjectInfo {
  const projectInfo = new ProjectInfo();
  projectInfo.name = o.name;
  projectInfo.version = o.version;
  projectInfo.area = o.area;
  projectInfo.repository = o.repository;
  projectInfo.urlGit = o.urlGit;
  projectInfo.description = o.description;
  return projectInfo;
}

class ProjectInfo {

  name = '';
  version = '';
  area = '';
  repository = '';
  urlGit = '';
  description = '';
  changedValue = false;
  projectType: Array<any> = [];
  mavenCertified = false;
  index = -1;

  public static fromObject(o: any): ProjectInfo {

    const projectInfo = fillProjectInfo(o);
    projectInfo.description = o.projectInfo.description;
    projectInfo.changedValue = false;
    projectInfo.projectType = o.projectInfo.projectType;
    projectInfo.mavenCertified = o.projectInfo.mavenCertified;
    projectInfo.urlGit = o.url;
    return projectInfo;
  }

  public static fromAppProjectInfo(o: AppProjectInfo): ProjectInfo {
    const projectInfo = fillProjectInfo(o);
    projectInfo.mavenCertified = o.mavenCertified;
    for (const type of o.projectType) {
      switch (type.item_text) {
        case 'Batch':
          projectInfo.projectType.push('BATCH');
          break;
        case 'Libreria':
          projectInfo.projectType.push('LIBRARY');
          break;
        case 'Online':
          projectInfo.projectType.push('ONLINE');
          break;
        case 'Servizio Web':
          projectInfo.projectType.push('WEB_SERVICE');
          break;
        case 'Libreria Client':
          projectInfo.projectType.push('CLIENT_LIBRARY');
          break;
      }
    }
    return projectInfo;
  }

}


class AppProjectInfo {

  name = '';
  version = '';
  area = '';
  repository = '';
  urlGit = '';
  description = '';
  changedValue = false;
  projectType: Array<any> = [];
  mavenCertified = false;
  index = -1;

  public static fromProjectInfo(o: ProjectInfo): AppProjectInfo {

    const appoProjectInfo = fillProjectInfo(o);
    appoProjectInfo.description = o.description;
    appoProjectInfo.changedValue = false;
    appoProjectInfo.mavenCertified = o.mavenCertified;
    appoProjectInfo.index = o.index;
    if (o.projectType !== [] && o.projectType !== null) {
      for (const type of o.projectType) {
        switch (type) {
          case 'BATCH':
            appoProjectInfo.projectType.push({item_id: 1, item_text: 'Batch'});
            break;
          case 'LIBRARY':
            appoProjectInfo.projectType.push({item_id: 2, item_text: 'Libreria'});
            break;
          case 'ONLINE':
            appoProjectInfo.projectType.push({item_id: 3, item_text: 'Online'});
            break;
          case 'WEB_SERVICE':
            appoProjectInfo.projectType.push({item_id: 4, item_text: 'Servizio Web'});
            break;
          case 'CLIENT_LIBRARY':
            appoProjectInfo.projectType.push({item_id: 5, item_text: 'Libreria Client'});
            break;
        }
      }
    }

    return appoProjectInfo;
  }
}

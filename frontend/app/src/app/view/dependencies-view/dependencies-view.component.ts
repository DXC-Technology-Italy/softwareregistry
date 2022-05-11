import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-dependencies-view',
  templateUrl: './dependencies-view.component.html',
  styleUrls: ['./dependencies-view.component.css']
})
export class DependenciesViewComponent implements OnInit {

  resourcebundle : any = {};
  lines: Array<string> = [];
  matches: Array<Match> = [];
  searching = false;
  searchingMaxVersion = false;
  matchedLibraries: Array<FoundLibrary> = [];
  matchedTempLibraries: Array<FoundLibrary> = [];
  dependecies: Map<string, Array<string>> = new Map<string, Array<string>>();
  isShownTempLib = false;

  constructor(private http: HttpClient, private authService: AuthenticationService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()    
  }

  ngOnInit(): void {
    this.fillLines();

  }

  private fillLines(): void {
    this.http.get(environment.apiUrl + '/file/dependencies/lines', this.authService.httpOptions()).subscribe(data => {

      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.lines.push(response[key]);
      }
    });
  }

  onSearchClean(): void {
    this.searching = false;
    this.searchingMaxVersion = false;
    (document.getElementById('depsSearch') as HTMLInputElement).value = '';
  }

  onSearchMaxVersionClean(): void {
    this.searching = false;
    this.searchingMaxVersion = false;
    (document.getElementById('searchMaxVersion') as HTMLInputElement).value = '';
    this.fillLines();
  }

  onSearchMaxVersion(): void {

    const searchTerm = (document.getElementById('searchMaxVersion') as HTMLInputElement).value;
    const url: string = environment.apiUrl + '/project/' + searchTerm + '/checkDependencies';
    this.matchedTempLibraries = [];
    this.matchedLibraries = [];
    this.searching = false;
    this.searchingMaxVersion = true;

    this.lines = [];
    this.http.get(url, this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        const library: FoundLibrary = FoundLibrary.fromObject(response[key], searchTerm);
        if (library.groupId.includes('templib')) {
          this.matchedTempLibraries.push(library);
        } else {
          this.matchedLibraries.push(library);
        }
      }
    });
  }


  onSearch(): void {
    const searchTerm = (document.getElementById('depsSearch') as HTMLInputElement).value;
    const url: string = environment.apiUrl + '/dependency/' + searchTerm;
    this.searching = true;
    this.searchingMaxVersion = false;
    this.matches = [];
    if (this.lines.length === 0) {
      this.fillLines();
    }

    this.http.get(url, this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.matches.push(Match.matchFromObject(response[key]));
      }
    });
  }

  showTempLib(): void {
    this.isShownTempLib = (document.getElementById('isShownTempLib') as HTMLInputElement).checked;


  }

}

class Match {
  public line = -1;
  public name = '';
  public group = '';
  public project = '';
  public version = '';
  public nexusUrl = '';
  public scope = '';

  constructor(line: number, name: string, project: string, group: string, version: string, nexusUrl: string, scope: string) {
    this.line = line;
    this.name = name;
    this.project = project;
    this.group = group;
    this.version = version;
    this.nexusUrl = nexusUrl;
    this.scope = scope;
  }

  public static matchFromObject(o: any): Match {
    return new Match(o.line, o.artifactId, o.parent, o.groupId, o.version, o.nexusUrl, o.scope);
  }
}

class FoundLibrary {

  version: string;
  groupId: string;
  parent: string;
  scope: string;
  projectType: string;
  artifactId: string;
  styleRed: boolean;
  actualVersion: string;
  nexusUrl: string;

  constructor(version: string, groupId: string, parent: string, scope: string, projectType: string, artifactId: string, styleRed: boolean,
              actualVersion: string, nexusUrl: string) {

    this.parent = parent;
    this.projectType = projectType;
    this.groupId = groupId;
    this.version = version;
    this.scope = scope;
    this.artifactId = artifactId;
    this.styleRed = styleRed;
    this.actualVersion = actualVersion;
    this.nexusUrl = nexusUrl;
  }

  public static fromObject(o: any, project: string): FoundLibrary {
    let styleRed = false;
    if (o.parent.toLowerCase() !== project.toLowerCase()) {
      styleRed = true;
    }

    return new FoundLibrary(o.version, o.groupId, o.parent, o.scope, o.projectType, o.artifactId, styleRed, o.actualVersion, o.nexusUrl);
  }
}

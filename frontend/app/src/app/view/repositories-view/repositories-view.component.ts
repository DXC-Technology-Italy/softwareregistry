import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-repositories-view',
  templateUrl: './repositories-view.component.html',
  styleUrls: ['./repositories-view.component.css']
})

export class RepositoriesViewComponent implements OnInit {

  resourcebundle : any = {};

  area = '';
  areas: string[] = [];

  repositories: Repository[] = [];

  constructor(private http: HttpClient, private authService: AuthenticationService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()
  }

  ngOnInit(): void {
    this.http.get(environment.apiUrl + '/repository?orderby=longName', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        const repository: Repository = Repository.fromObject(response[key]);
        this.repositories.push(repository);
      }
    });
    this.http.get(environment.apiUrl + '/distinctAreas', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.areas.push(response[key]);
      }
    });
  }


  download(): void {

    const options: any = this.authService.httpOptions();
    options.responseType = 'arraybuffer';
    // this.http.get(environment.apiUrl + "/los/" + this.bigcode + "/csv/", {responseType: 'arraybuffer'}).subscribe(data => {
    this.http.get(environment.apiUrl + '/repository/csv?orderby=longName', options).subscribe(data => {
      console.log(data);

      const blob = new Blob([data as any], {type: 'text/csv'});
      const filename = 'ListaRepository.csv';
      const objectUrl = window.URL.createObjectURL(blob);
      const a: HTMLAnchorElement = document.createElement('a');

      a.href = objectUrl;
      a.download = filename;
      a.click();
      document.body.appendChild(a);
      window.URL.revokeObjectURL(objectUrl);
      a.remove();

    });

  }

}

class Repository {
  public name = '';
  public longName = '';
  public url = '';
  public projects = '';
  public area = '';
  public kind = '';

  public static fromObject(o: any): Repository {

    const repository = new Repository();
    repository.name = o.name;
    repository.longName = o.longName;
    repository.url = o.url;
    repository.area = o.area;
    repository.kind = o.kind;
    repository.projects = o.projects;
    return repository;
  }
}


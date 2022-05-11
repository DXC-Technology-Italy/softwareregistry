import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';

import {environment} from 'src/environments/environment';
import {DownloadService} from '../../service/util/download.service';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-searcher-view',
  templateUrl: './code-search-view.component.html',
  styleUrls: ['./code-search-view.component.css']
})
export class CodeSearchViewComponent implements OnInit {

  resourcebundle : any = {};
  area = '';
  repository = '';
  extension = '';
  project = '';
  keyword = '';
  type = '';
  limitResult = 100;
  rowCount = 0;
  filename = '';

  repositories: string[] = [];
  areas: string[] = [];
  projects: string[] = [];
  extensions: string[] = ['txt', 'java', 'xml', 'sql', 'sh', 'py', 'cob', 'properties', 'pco', 'cpy',
    'cbl', 'js', 'css', 'jsp', 'htc', 'html', 'htm', 'xsd', 'wsdl', 'xslt'];
  types: string[] = [];
  results: SearchResult[] = [];
  columnsOrder: Map<string, string> = new Map<string, string>([
    ['area', ''],
    ['repository', ''],
    ['project', ''],
    ['kind', ''],
    ['file', '']
  ]);

  constructor(private http: HttpClient, private authService: AuthenticationService, private downloadService: DownloadService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()
    this.types = this.resourcebundle.lists.technologies
  }

  ngOnInit(): void {

    this.http.get(environment.apiUrl + '/distinctAreas', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.areas.push('area' + response[key]);
      }
    });
    this.http.get(environment.apiUrl + '/distinctRepositories', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.repositories.push(response[key]);
      }
    });
    this.http.get(environment.apiUrl + '/distinctProject', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        this.projects.push(response[key]);
      }
    });
  }

  clear(): void {
    this.area = '';
    this.repository = '';
    this.extension = '';
    this.project = '';
    this.keyword = '';
    this.type = '';
    this.filename = '';
    this.rowCount = 0;
    this.limitResult = 100;
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['file', '']
    ]);
    this.results = [];
  }

  search(): void {
    this.results = [];
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['file', '']
    ]);
    const {
      searchKeyword,
      searchArea,
      searchRepository,
      searchProject,
      searchExtension,
      searchType,
      searchFilename
    } = this.extractSearchAttributes();
    const url: string = environment.apiUrl + '/getMatchingFiles?' + searchArea + searchRepository + searchProject +
      searchExtension + searchType + searchFilename + '&limitResult=' + this.limitResult;
    this.http.post(url, searchKeyword, this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        const result: SearchResult = SearchResult.FromObject(response[key]);
        if (result.area != null) {
          this.results.push(result);
        }
      }
      this.rowCount = this.results.length;
    });
  }

  private extractSearchAttributes(): any {
    let searchKeyword;
    if (!(document.getElementById('wholeWord') as HTMLInputElement).checked && !this.keyword.includes(' ')) {
      searchKeyword = '.*' + this.keyword.replace(/[@|\\{}()[\]^$+*?.]/g, '\\$&') + '.*';
    } else {
      searchKeyword = this.keyword.replace(/[@|\\{}()[\]^$+*?.]/g, '\\$&');
    }
    const searchArea = this.area !== '' ? '&area=' + this.area : '';
    const searchRepository = this.repository !== '' ? '&repository=' + this.repository : '';
    const searchProject = this.project !== '' ? '&project=' + this.project + '(_WRAPPER)?' : '';
    const searchExtension = this.extension !== '' ? '&extension=' + this.extension : '';
    const searchType = this.type !== '' ? '&kind=' + this.type : '';
    const searchFilename = this.filename !== '' ? '&filename=' + this.filename : '';
    return {searchKeyword, searchArea, searchRepository, searchProject, searchExtension, searchType, searchFilename};
  }

  setOrder(column: string): void {

    const order = this.columnsOrder.get(column);
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['file', '']
    ]);
    this.columnsOrder.set(column, order === 'asc' ? 'desc' : 'asc');


  }

  download(): void {
    const filename = 'MatchingFiles_' + this.keyword.replace(/\s/g, '_') + '.csv';
    this.downloadService.downloadUtil(filename, 'data_table');
  }


}

class SearchResult {

  area = '';
  repository = '';
  project = '';
  file = '';
  url = '';
  kind = '';

  public static FromObject(o: any): SearchResult {
    const searchResult = new SearchResult();
    try {
      searchResult.area = o.area;
      searchResult.repository = o.repository.replace('/', '');
      searchResult.project = o.project.replace('/', '');
      searchResult.kind = o.kind;
      searchResult.file = o.filepath.replace(environment.softwareRegistryPath + '/', '');
      searchResult.url = environment.gitlabUrl + '/' + searchResult.file.replace(searchResult.repository, searchResult.repository + '/blob/master');
      return searchResult;
    } catch {
      return searchResult;
    }

  }
}

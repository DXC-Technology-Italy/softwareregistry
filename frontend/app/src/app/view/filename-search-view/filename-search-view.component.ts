import {Component, OnInit} from '@angular/core';

import {HttpClient} from '@angular/common/http';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {DownloadService} from '../../service/util/download.service';

@Component({
  selector: 'app-filename-search-view',
  templateUrl: './filename-search-view.component.html',
  styleUrls: ['./filename-search-view.component.css']
})
export class FilenameSearchViewComponent implements OnInit {

  filename = '';
  area = '';
  areas: string[] = [];
  repository = '';
  repositories: string[] = [];
  kind = '';
  limitResult = 100;
  rowCount = 0;
  kinds: string[] = ['Java', 'Cobol', 'Database', 'SQL', 'Batch', 'Python', 'Altro'];
  results: FileSearchResult[] = [];
  columnsOrder: Map<string, string> = new Map<string, string>([
    ['filename', ''],
    ['area', ''],
    ['repository', ''],
    ['project', ''],
    ['kind', ''],
  ]);

  noResults = false;

  constructor(private http: HttpClient, private authService: AuthenticationService, private downloadService: DownloadService) {
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
  }

  search($event: any): void {
    this.results = [];
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['filename', '']
    ]);
    const {searchKeyword, searchArea, searchRepository, searchType} = this.extractSearchAttributes();
    const url: string = environment.apiUrl + '/getMatchingFileNames?filename=' + searchKeyword + searchArea + searchRepository +
      searchType + '&limitResult=' + this.limitResult;

    this.http.get(url, this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      if (Object.keys(data).length === 0) {
        this.noResults = true;
      } else {
        this.noResults = false;
        for (const key of Object.keys(data)) {
          const result: FileSearchResult = FileSearchResult.FromObject(response[key]);
          if (result.area != null) {
            this.results.push(result);
          }
        }
      }
      this.rowCount = this.results.length;
    });

  }

  private extractSearchAttributes(): any{
    let searchKeyword: string;
    if (!(document.getElementById('wholeWord') as HTMLInputElement).checked) {
      searchKeyword = '.*' + this.filename + '.*';
    } else {
      searchKeyword = this.filename;
    }
    const searchArea = this.area !== '' ? '&area=' + this.area : '';
    const searchRepository = this.repository !== '' ? '&repository=' + this.repository : '';
    const searchType = this.kind !== '' ? '&kind=' + this.kind : '';
    return {searchKeyword, searchArea, searchRepository, searchType};
  }

  clear($event: any): void {
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['filename', '']
    ]);
    this.noResults = false;
    this.filename = '';
    this.area = '';
    this.kind = '';
    this.repository = '';
    this.limitResult = 100;
    this.rowCount = 0;
    this.results = [];
  }

  setOrder(column: string): void {
    const order = this.columnsOrder.get(column);
    this.columnsOrder = new Map<string, string>([
      ['area', ''],
      ['repository', ''],
      ['project', ''],
      ['kind', ''],
      ['filename', '']
    ]);
    this.columnsOrder.set(column, order === 'asc' ? 'desc' : 'asc');
  }


  download($event: any): void {
    const filename = 'MatchingFiles_' + this.filename.replace(/\s/g, '_') + '.csv';
    this.downloadService.downloadUtil(filename, 'data_table');
  }

}

class FileSearchResult {

  area = '';
  repository = '';
  project = '';
  filename = '';
  url = '';
  kind = '';

  public static FromObject(o: any): FileSearchResult {
    const searchResult = new FileSearchResult();
    try {
      searchResult.filename = o.filepath.replace(environment.softwareRegistryPath + '/', '');
      searchResult.area = o.area;
      searchResult.repository = o.repository.replace('/', '');
      searchResult.project = o.project.replace('/', '');
      searchResult.kind = o.kind;
      searchResult.url = environment.gitlabUrl + '/' + searchResult.filename.replace(searchResult.repository, searchResult.repository + '/blob/master');
      return searchResult;
    } catch {
      return searchResult;
    }
  }
}

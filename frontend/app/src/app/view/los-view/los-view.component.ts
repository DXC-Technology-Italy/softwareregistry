import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {DownloadService} from '../../service/util/download.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-los-view',
  templateUrl: './los-view.component.html',
  styleUrls: ['./los-view.component.css']
})
export class LosViewComponent implements OnInit {

  bigcode = '';
  losGenerated = false;
  noResult = false;

  matches: Array<Match> = [];

  constructor(private http: HttpClient, private authService: AuthenticationService, private downloadService: DownloadService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.bigcode = params.bigcode;
      if (this.bigcode !== '' && this.bigcode !== undefined) {
        this.search();
      }
    });
  }

  clear($event: any): void {
    this.bigcode = '';
    this.noResult = false;
    this.losGenerated = false;
    this.noResult = false;
    this.matches = [];
  }

  search(): void {
    this.matches = [];
    this.http.post(environment.apiUrl + '/los/' + this.bigcode, this.authService.httpOptions()).subscribe(data => {

      const response = Object.create(data);

      if (Object.keys(data).length === 0) {
        this.noResult = true;
        this.losGenerated = false;
      } else {
        for (const key of Object.keys(data)) {
          this.matches.push(Match.matchFromObject(response[key], this.bigcode));
        }
        this.losGenerated = true;
        this.noResult = false;
      }

    });
  }

  download($event: any): void {
    const filename = 'MEV-NP-LOS-' + this.bigcode + '.csv';
    this.downloadService.downloadUtil(filename, 'data_table');
  }
}

class Match {
  public name = '';
  public path = '';
  public repository = '';
  public area = '';
  public action = '';
  public url = '';
  public extension = '';

  constructor(name: string, path: string, repository: string, action: string, bigcode: string) {
    const re = /(?:\.([^.]+))?$/;
    this.name = name;
    this.path = path;
    this.repository = repository;
    this.area = 'area' + bigcode.substr(5, 4);
    if (action === 'ADDED') {
      this.action = 'Aggiunto';
    }
    if (action === 'MODIFIED') {
      this.action = 'Modificato';
    }
    if (action === 'RENAMED') {
      this.action = 'Spostato/Rinominato';
    }
    if (action === 'DELETED') {
      this.action = 'Cancellato';
    }

    this.url = environment.gitlabUrl + '/' + this.area + '/' + this.repository + '/blob/' + 'develop-' + bigcode + '/' + path + '/' + name;
    // @ts-ignore
    this.extension = re.exec(name)[1];
  }

  public static matchFromObject(o: any, bigcode: string): Match {
    return new Match(o.name, o.path, o.repository, o.action, bigcode);
  }
}

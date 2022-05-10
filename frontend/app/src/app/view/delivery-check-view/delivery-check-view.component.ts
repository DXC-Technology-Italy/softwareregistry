import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {AuthenticationService} from '../../service/auth/authentication.service';
import {DownloadService} from '../../service/util/download.service';
import {ActivatedRoute} from '@angular/router';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-delivery-check-view',
  templateUrl: './delivery-check-view.component.html',
  styleUrls: ['./delivery-check-view.component.css']
})
export class DeliveryCheckViewComponent implements OnInit {

  resourcebundle : any = {};

  bigcode = '';
  deliveryCheckGenerated = false;
  noResult = false;

  releases: Array<Release> = [];
  selectedReleaseItems: Array<ReleaseItem> = [];
  currentIndex = 0;

  constructor(private http: HttpClient, private authService: AuthenticationService, private downloadService: DownloadService,
              private route: ActivatedRoute) {
    var localize = new Localize()
    this.resourcebundle = localize.get()
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.bigcode = params.bigcode;
      if (this.bigcode !== '' && this.bigcode !== undefined) {
        this.search();
      }
    });
  }

  clear(): void {
    this.bigcode = '';
    this.noResult = false;
    this.deliveryCheckGenerated = false;
    this.noResult = false;
    this.releases = [];
  }

  search(): void {
    this.releases = [];
    this.http.post(environment.apiUrl + '/deliverycheck/' + this.bigcode, this.authService.httpOptions()).subscribe(data => {

      const response = Object.create(data);

      if (Object.keys(data).length === 0) {
        this.noResult = true;
        this.deliveryCheckGenerated = false;
      } else {
        for (const key of Object.keys(data)) {
          this.releases.push(Release.ReleaseFromObject(response[key]));
        }
        this.deliveryCheckGenerated = true;
        this.noResult = false;
      }

    });
  }

  download(i: number): void {

    const filename = 'MEV-NP-DeliveryCheck-' + this.bigcode + '.csv';
    this.downloadService.downloadUtil(filename, 'data_table-' + i);

  }

  setSelectedRelease(i: number): void {
    this.selectedReleaseItems = this.releases[i].releaseItems;
    this.currentIndex = i;
  }
}

class Release {

  public author = '';
  public date = '';
  public mergeRequestType = '';
  public nexusUrls: string[] = [];
  public releaseItems: ReleaseItem[] = [];

  constructor(author: string, date: string, mergeRequestType: string, nexusUrls: string[], releaseItems: ReleaseItem[]) {
    this.author = author;
    this.date = date;
    this.mergeRequestType = mergeRequestType;
    this.nexusUrls = nexusUrls;
    this.releaseItems = releaseItems;
  }

  public static ReleaseFromObject(o: any): Release {
    return new Release(o.author, o.date, o.mergeRequestType, o.nexusUrls, o.releaseItems);
  }

}

class ReleaseItem {
  public filename = '';
  public repository = '';
  public sha1sum = '';
  public pathMef = '';

  constructor(filename: string, repository: string, sha1sum: string, pathMef: string) {
    this.filename = filename;
    this.repository = repository;
    this.sha1sum = sha1sum;
    this.pathMef = pathMef;
  }

  public static ReleaseItemFromObject(o: any): ReleaseItem {
    return new ReleaseItem(o.filename, o.repository, o.sha1sum, o.pathMef);
  }


}

import {Component} from '@angular/core';
import {AuthenticationService} from '../../service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent {

  resourcebundle : any = {};

  gitlabUrl = environment.gitlabUrl;
  jenkinsUrl = environment.jenkinsUrl;
  sonarUrl = environment.sonarUrl;
  nexusUrl = environment.nexusUrl;

  constructor(private authService: AuthenticationService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()    
  }

  isAdmin(): boolean {
    return this.authService.getRole() === 'ADMIN';
  }
}

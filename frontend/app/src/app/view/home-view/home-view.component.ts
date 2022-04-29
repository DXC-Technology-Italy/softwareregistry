import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../service/auth/authentication.service';
import {environment} from 'src/environments/environment';



@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css']
})
export class HomeViewComponent implements OnInit {

  gitlabUrl  = environment.gitlabUrl
  jenkinsUrl = environment.jenkinsUrl
  sonarUrl   = environment.sonarUrl
  nexusUrl   = environment.nexusUrl

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
  }
  isAdmin(): boolean {
    return this.authService.getRole() === 'ADMIN';
  }
}

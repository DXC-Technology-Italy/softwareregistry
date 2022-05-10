import { Component } from '@angular/core';
import { AuthenticationService } from './service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Software Registry';
  VERSION = '2.1.0';
  settingsDialogVisibility = 'none';
  themes = [{ name: 'Light', value: 'light' }, { name: 'Dark', value: 'dark' } ];
  selectedTheme = '';
  gitlabUrl  = environment.gitlabUrl;
  jenkinsUrl = environment.jenkinsUrl;
  sonarUrl   = environment.sonarUrl;
  nexusUrl   = environment.nexusUrl;
  localize   = new Localize();
  resourcebundle : any = {} 

  constructor(private loginService: AuthenticationService) {
    this.selectedTheme = localStorage.getItem('theme') || 'light';
    this.resourcebundle = this.localize.get()
  }
  getSelectedTheme(): string {
    this.selectedTheme = this.loginService.selectedTheme();
    return this.selectedTheme;
  }

  getRole(): string {
    return this.loginService.getRole() || '';
  }

  getUsername(): string {
    return this.loginService.getUsername() || '';
  }

  selectTheme($event: any): void {
    this.selectedTheme = $event.target.value;
    localStorage.setItem('theme', this.selectedTheme);
    this.loginService.saveSelectedTheme();
  }
  showSettingsDialog(): void {
    this.settingsDialogVisibility = 'block';
  }

  hideSettingsDialog(): void {
    this.settingsDialogVisibility = 'none';
  }

  logout(): void {
    this.loginService.logOut();
  }
  isUserLoggedIn(): boolean {
    return this.loginService.isUserLoggedIn();
  }
  isAdmin(): boolean{
      return this.getRole() === 'ADMIN';
  }
}

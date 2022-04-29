import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DependenciesViewComponent } from './view/dependencies-view/dependencies-view.component';
import { RepositoriesViewComponent } from './view/repositories-view/repositories-view.component';
import { ProjectsViewComponent } from './view/projects-view/projects-view.component';
import { CodeSearchViewComponent } from './view/code-search-view/code-search-view.component';
import { HomeViewComponent } from './view/home-view/home-view.component';
import { LosViewComponent } from './view/los-view/los-view.component';
import { FilenameSearchViewComponent } from './view/filename-search-view/filename-search-view.component';
import { AuthGuardService } from './service/auth/auth-guard.service';

import { LoginViewComponent } from './view/login-view/login-view.component';
import {UserViewComponent} from './view/user-view/user-view.component';
import {DeliveryCheckViewComponent} from './view/delivery-check-view/delivery-check-view.component';

const routes: Routes = [
  { path: 'repositories', component: RepositoriesViewComponent, canActivate: [AuthGuardService] },
  { path: 'dependencies', component: DependenciesViewComponent, canActivate: [AuthGuardService] },
  { path: 'los', component: LosViewComponent, canActivate: [AuthGuardService] },
  { path: 'home', component: HomeViewComponent, canActivate: [AuthGuardService] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'projects-info', component: ProjectsViewComponent, canActivate: [AuthGuardService] },
  { path: 'code-search', component: CodeSearchViewComponent, canActivate: [AuthGuardService] },
  { path: 'filename-search', component: FilenameSearchViewComponent, canActivate: [AuthGuardService] },
  { path: 'user', component: UserViewComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginViewComponent },
  { path: 'delivery-check', component: DeliveryCheckViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

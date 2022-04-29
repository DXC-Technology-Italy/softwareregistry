import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RepositoriesViewComponent } from './view/repositories-view/repositories-view.component';
import { DependenciesViewComponent } from './view/dependencies-view/dependencies-view.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProjectsViewComponent } from './view/projects-view/projects-view.component';
import { ProjectTableFilterPipe } from './view/projects-view/table-filter.pipe';
// import { RepositoryComboFilterPipe } from './view/searcher-view/table-filter.pipe';
import { RepositoryTableFilterPipe } from './view/repositories-view/table-filter.pipe';
import { CodeSearchViewComponent } from './view/code-search-view/code-search-view.component';
import { FormsModule } from '@angular/forms';
import { HomeViewComponent } from './view/home-view/home-view.component';
import { LosViewComponent } from './view/los-view/los-view.component';
import { SortDirective } from './directive/sort.directive';
import { FilenameSearchViewComponent } from './view/filename-search-view/filename-search-view.component';
import { BasicAuthHtppInterceptorService } from './basic-auth-interceptor.service';
import { LoginViewComponent } from './view/login-view/login-view.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { DeliveryCheckViewComponent } from './view/delivery-check-view/delivery-check-view.component';
import { UserViewComponent } from './view/user-view/user-view.component';

@NgModule({
  declarations: [
    AppComponent,
    RepositoriesViewComponent,
    DependenciesViewComponent,
    ProjectsViewComponent,
    ProjectTableFilterPipe,
    RepositoryTableFilterPipe,
    CodeSearchViewComponent,
    HomeViewComponent,
    LosViewComponent,
    SortDirective,
    LoginViewComponent,
    FilenameSearchViewComponent,
    DeliveryCheckViewComponent,
    UserViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [{
      provide: HTTP_INTERCEPTORS, useClass: BasicAuthHtppInterceptorService, multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }

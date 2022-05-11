import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';
import {environment} from 'src/environments/environment';
import {Localize} from 'src/app/i18n/localize';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {

  resourcebundle : any = {};

  users: User[] = [];
  selectedUser: User = new User();

  constructor(private http: HttpClient, private authService: AuthenticationService) {
    var localize = new Localize()
    this.resourcebundle = localize.get()
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void{
    this.http.get(environment.apiUrl + '/user', this.authService.httpOptions()).subscribe((data) => {
      const response = Object.create(data);
      for (const key of Object.keys(data)) {
        const user: User = User.fromObject(response[key]);
        this.users.push(user);
      }
    });
  }
  httpOptions(): {} {
    let httpOptions = {};
    if (this.authService.isUserLoggedIn()) {
      httpOptions = {
        headers: new HttpHeaders({
          Authorization: localStorage.getItem('token') || ''
        })
      };
    }
    return httpOptions;
  }

  setSelectedUser(i: number): void{
    this.selectedUser = this.users[i];

  }

  changeModel($event: any): void{
    this.selectedUser.role = $event.target.value;
  }

  saveChange(): void{
    this.http
      // tslint:disable-next-line:max-line-length
      .post<any>(environment.apiUrl + '/user/updateRole?username=' + this.selectedUser.username + '&role=' + this.selectedUser.role, this.httpOptions()).subscribe(
      data => {
        console.log(data);
        return data;
      }
    );
  }

  clear(): void{
    this.users = [];
    this.getUsers();
  }

}

class User{

 public username = '';
 public role = '';

  public static fromObject(o: any): User {
    const user = new User();
    user.username = o.username;
    user.role = o.role;
    return user;
  }

}

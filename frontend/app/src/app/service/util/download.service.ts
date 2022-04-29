import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class DownloadService {
  constructor(private httpClient: HttpClient, private router: Router) {
  }


  downloadUtil(filename: string, idTable: string): void {
    let data = '';
    const table = document.getElementById(idTable);
    // @ts-ignore
    const tr = table.children[0].children[0];
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < tr.children.length; i++) {
      data += tr.children[i].textContent + ';';
    }
    data = data.substring(0, data.length - 1) + '\n';
    // @ts-ignore
    const tbody = table.children[1];
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < tbody.children.length; i++) {
      // tslint:disable-next-line:prefer-for-of
      for (let j = 0; j < tbody.children[i].children.length; j++) {
        // @ts-ignore
        if ( tbody.children[i].children[j].firstChild.checked !== undefined ){
          // @ts-ignore
          data += (tbody.children[i].children[j].firstChild.checked) ? 'Si;' : 'No;';
        }else if ( tbody.children[i].children[j].children.length > 1){
          // tslint:disable-next-line:prefer-for-of
          for (let h = 0; h < tbody.children[i].children[j].children.length; h++){
            if (h === tbody.children[i].children[j].children.length - 1) {
              data += tbody.children[i].children[j].children[h].textContent + ';';
            }else{
              data += tbody.children[i].children[j].children[h].textContent + ',';
            }

          }
        }else {

          data += tbody.children[i].children[j].textContent + ';';
        }
      }
      data = data.substring(0, data.length - 1) + '\n';
    }
    data = data.substring(0, data.length - 1) + '\n';
    const blob = new Blob([data as any], {type: 'text/csv'});
    const objectUrl = window.URL.createObjectURL(blob);
    const a: HTMLAnchorElement = document.createElement('a') as HTMLAnchorElement;

    a.href = objectUrl;
    a.download = filename;
    a.click();
    document.body.appendChild(a);
    window.URL.revokeObjectURL(objectUrl);
    a.remove();
  }

}

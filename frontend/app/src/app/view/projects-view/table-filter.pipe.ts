
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'projectTableFilter'
})
export class ProjectTableFilterPipe implements PipeTransform {

  transform(list: any[], filters: Filter): any[] {
    // @ts-ignore
    const keys: string[] = Object.keys(filters).filter(key => filters[key]);

    // @ts-ignore
    // tslint:disable-next-line:max-line-length
    return keys.length ? list.filter(projectInfo => keys.every(key => ((projectInfo[key] === null) ? 'false' : String(projectInfo[key]) ) === filters[key])) : list;

    }



}

interface Filter{
area: string;
repository: string;
mavenCertified: boolean | undefined;
}

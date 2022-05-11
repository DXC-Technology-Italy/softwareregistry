import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'repositoryTableFilter'
})
export class RepositoryTableFilterPipe implements PipeTransform {

  transform(list: any[], filters: Object) {
    const keys = Object.keys(filters).filter(key => filters[key as keyof Object]);
    
    return keys.length ? list.filter(repository => keys.every(key => repository.area === "area" + filters[key as keyof Object])) : list;
  }

}

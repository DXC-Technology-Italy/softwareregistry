export class Sort{

  private sortOrder = 1;
  private collator = new Intl.Collator(undefined, {
    numeric: true,
    sensitivity : 'base'
  });

  public startSort(property: any, order: any, type= ''): any{
    if (order === 'desc'){
      this.sortOrder = -1;
    }
    return(a: any, b: any) => {
      if (type === 'date'){
        return this.sortData(new Date(a[property]), new Date(b[property]));
      }else{
        return this.collator.compare(a[property], b[property]) * this.sortOrder;
      }
    };
  }

  private sortData(a: any, b: any): number{
    if (a < b){
      return -1 * this.sortOrder;
    }else if (a > b){
      return this.sortOrder;
    }else{
      return 0;
    }
  }
}


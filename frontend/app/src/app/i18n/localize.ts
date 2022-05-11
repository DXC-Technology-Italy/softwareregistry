import {it} from 'src/app/i18n/it-IT'
import {en} from 'src/app/i18n/en-EN'

export class Localize {

  resourcebundle = {}

  constructor() {
    var userLang = navigator.language;

    if (userLang == "it-IT" || userLang == "it") {
      this.resourcebundle = it
    } else {
      this.resourcebundle = en
    }
  }

  get(): Object {
    return this.resourcebundle;
  }

}

import {it} from 'src/app/i18n/it-IT'
import {en} from 'src/app/i18n/en-EN'

export class Localize {

  resourcebundle = {}

  constructor() {
    var userLang = navigator.language;
    console.log(userLang);

    if (userLang == "it-IT" || userLang == "it") {
      this.resourcebundle = it
    } else if (userLang == "en-US" || userLang == "en-EN" || userLang == "en") {
      this.resourcebundle = en
    }
  }

  get(): Object {
    return this.resourcebundle;
  }

}

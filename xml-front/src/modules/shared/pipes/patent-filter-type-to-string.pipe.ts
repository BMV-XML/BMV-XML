import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'patentFilterTypeToString'
})
export class PatentFilterTypeToStringPipe implements PipeTransform {

  transform (value: string | null): string {
    if (value === null)
      return ""
    switch (value){
      case 'pod_ime':
        return 'Podnosilac'
      case 'pun_ime':
        return 'Punomoćnik'
      case 'pro_ime':
        return 'Pronalazač'
      case 'child':
        return 'Izdvojena'
      case 'additional':
        return 'Dodatna'
      case 'naslov':
        return 'Naslov'
      case 'datum_prijave':
        return 'Datum prijave'
      case 'broj_prijave':
        return 'Broj prijave'
      default:
        return 'Filter'
    }
  }

}

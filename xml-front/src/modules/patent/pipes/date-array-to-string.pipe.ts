import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateArrayToString'
})
export class DateArrayToStringPipe implements PipeTransform {

  transform (value: number[]): string {
    return value[0] + '. ' + value[1] + '. ' + value[2] + '.'
  }

}

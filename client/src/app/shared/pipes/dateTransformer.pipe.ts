import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'dateTransformer'
})

export class DateTransformerPipe implements PipeTransform {
  transform(value: string) {
    const date = value.substr(0, 10);
    const time = value.substr(11, 8);

    return `${date} at ${time}`;
  }
}

import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ErrorService {
  errorListener = new Subject<string>();

  triggerErrorMessage(err: string) {
    this.errorListener.next(err);
    this.setErrorTimeOut();
  }
  private setErrorTimeOut() {
    setTimeout(() => {
      this.errorListener.next('');
    }, 2000);
  }
}

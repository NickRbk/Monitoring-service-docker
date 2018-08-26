import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EnvConst} from '../constants/env.const';

@Injectable({providedIn: 'root'})
export class SocialMediaService {

  constructor(private httpClient: HttpClient,
              private envConst: EnvConst) {
  }

  updateTwitterProfile(id: number, alias) {
    return new Promise((resolve, reject) => {
      this.httpClient.post(this.envConst.BACKEND_URL + `/api/users/${id}/media`, {alias})
        .subscribe(
          () => resolve(),
          err => reject(err)
        );
    });
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ErrorService} from './error.service';
import {EnvConst} from '../constants/env.const';

@Injectable({providedIn: 'root'})
export class TweetService {
  constructor(private httpClient: HttpClient,
              private envConst: EnvConst,
              private errorService: ErrorService) {
  }

  fetchTweets(page: number, size: number, orderBy: string, direction: string) {
    let url = this.envConst.BACKEND_URL + `/api/media?page=${page}&size=${size}`;
    url = orderBy ? `${url}&orderBy=${orderBy}&d=${direction}` : url;

    return new Promise((resolve) => {
      this.httpClient.get(url)
        .subscribe(
          tweets => resolve(tweets),
          err => this.errorService.triggerErrorMessage(err['massage'])
        );
    });
  }
}

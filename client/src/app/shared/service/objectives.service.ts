import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Customer} from '../model/customer.model';
import {ErrorService} from './error.service';
import {SocialMediaService} from './social-media.service';
import {Router} from '@angular/router';
import {EnvConst} from '../constants/env.const';

@Injectable()
export class ObjectivesService {
  private objectivesListener = new Subject<any>();
  private objective;

  constructor(private httpClient: HttpClient,
              private router: Router,
              private socialMediaService: SocialMediaService,
              private envConst: EnvConst,
              private errorService: ErrorService) {}

  getObjectivesListener() {
    return this.objectivesListener.asObservable();
  }

  getObjective() {
    return this.objective;
  }

  setObjective(objective) {
    return this.objective = objective;
  }

  getObjectives() {
    this.httpClient.get<Customer>(this.envConst.BACKEND_URL + '/api/users/')
      .subscribe(
        objectives => this.objectivesListener.next(objectives),
        err => this.errorService.triggerErrorMessage(err['massage'])
      );
  }

  checkAlias(alias: string) {
    return new Promise((resolve, reject) => {
      this.httpClient.get<boolean>(this.envConst.BACKEND_URL + `/api/media/${alias}`)
        .subscribe(
          (isExist: boolean) => isExist ? resolve() : reject(),
          err => this.errorService.triggerErrorMessage(err['massage'])
        );
    });
  }

  save(body) {
    return new Promise((resolve) => {
      this.httpClient.post(this.envConst.BACKEND_URL + '/api/users/', body).subscribe(
        (id: number) => resolve(id),
        err => this.errorService.triggerErrorMessage(err['massage'])
      );
    });
  }

  update(id: number, body) {
    return new Promise((resolve) => {
      this.httpClient.patch(this.envConst.BACKEND_URL + `/api/users/${id}`, body).subscribe(
        () => resolve(),
        err => this.errorService.triggerErrorMessage(err['massage'])

      );
    });
  }

  delete(id: number) {
    this.httpClient.delete(this.envConst.BACKEND_URL + `/api/users/${id}`)
      .subscribe(
        () => this.router.navigate(['/objectives']),
        err => this.errorService.triggerErrorMessage(err['massage'])
      );
  }
}

import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Customer} from '../model/customer.model';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {HttpHeaders} from '@angular/common/http';
import {CustomerService} from './customer.service';
import {ErrorService} from './error.service';
import {EnvConst} from '../constants/env.const';
import {CustomerSignup} from '../model/customer-signup.model';

@Injectable()
export class AuthService {
  private isAuthenticated = false;
  private authStatusListener = new Subject<boolean>();
  private token: string;
  private tokenTimer;

  constructor(private httpClient: HttpClient,
              private router: Router,
              private errorService: ErrorService,
              private envConst: EnvConst,
              private customerService: CustomerService) {}

  getToken() {
    return this.token;
  }

  getAuthStatusListener() {
    return this.authStatusListener.asObservable();
  }

  signUp(customer: CustomerSignup) {
    this.httpClient.post(this.envConst.BACKEND_URL + '/auth/sign-up', customer)
      .subscribe(
        () => this.logIn(customer.email, customer.password),
        err => this.errorService.triggerErrorMessage(err['message'])
      );
  }

  logIn(email: string, password: string) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      observe: 'response' as 'response'
    };

    this.httpClient.post<{email: string, password: string}>(this.envConst.BACKEND_URL + '/login',
      {email, password}, httpOptions)
      .subscribe(
        res => {
          this.token = res.headers.get('authorization');
          const expirationDate = new Date(+res.headers.get('expires'));
          this.isAuthenticated = true;
          this.authStatusListener.next(true);
          this.saveAuthData(this.token, expirationDate);

          const now = new Date();
          const expiresIn: number = this.getAuthData().expirationDate.getTime() - now.getTime();
          this.setAuthTimer(expiresIn);

          this.customerService.fetchCustomer();

          this.router.navigate(['/objectives']);
        },
        err => this.errorService.triggerErrorMessage(err['message'])
      );
  }

  logout() {
    this.token = null;
    this.isAuthenticated = false;
    this.authStatusListener.next(false);

    this.customerService.setCurrentUser(null);
    this.customerService.currentUserListener.next(null);

    this.clearAuthData();
    this.router.navigate(['/']);
    clearTimeout(this.tokenTimer);
  }

  updateCustomer(customer: CustomerSignup) {
    this.httpClient.patch(this.envConst.BACKEND_URL + '/auth', customer)
      .subscribe(
        () => {
          localStorage.setItem('c:email', customer.email);
          localStorage.setItem('c:firstName', customer.firstName);
          localStorage.setItem('c:lastName', customer.lastName);
          localStorage.setItem('c:phoneNumber', customer.phoneNumber);

          this.logIn(customer.email, customer.password);
        },
        err => this.errorService.triggerErrorMessage(err['message'])
      );
  }

  deleteCustomer() {
    this.httpClient.delete(this.envConst.BACKEND_URL + '/auth')
      .subscribe(
        () => this.logout(),
        err => this.errorService.triggerErrorMessage(err['message'])
      );
  }

  autoAuthUser() {
    const authInfo = this.getAuthData();
    if (!authInfo) {
      return;
    }
    const now = new Date();
    const expiresIn: number = authInfo.expirationDate.getTime() - now.getTime();
    if (expiresIn > 0) {
      this.token = authInfo.token;
      this.setAuthTimer(expiresIn);

      this.isAuthenticated = true;
      this.authStatusListener.next(true);

      this.customerService.setCurrentUser(authInfo.currentUser);
      this.customerService.currentUserListener.next(authInfo.currentUser);
    }
  }

  private setAuthTimer(duration: number) {
    console.log('Setting timer: ' + duration);
    this.tokenTimer = setTimeout(() => {
      this.logout();
      this.router.navigate(['/login']);
    }, duration);
  }

  getIsAuth() {
    return this.isAuthenticated;
  }

  private saveAuthData(token: string, expirationDate: Date) {
    localStorage.setItem('token', token);
    localStorage.setItem('expiration', expirationDate.toISOString());
  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('expiration');

    localStorage.removeItem('c:id');
    localStorage.removeItem('c:email');
    localStorage.removeItem('c:firstName');
    localStorage.removeItem('c:lastName');
    localStorage.removeItem('c:phoneNumber');
  }

  private getAuthData() {
    const token = localStorage.getItem('token');
    const expirationDate = localStorage.getItem('expiration');
    const currentUser: Customer = new Customer(
      +localStorage.getItem('c:id'),
      localStorage.getItem('c:email'),
      localStorage.getItem('c:firstName'),
      localStorage.getItem('c:lastName'),
      localStorage.getItem('c:phoneNumber')
    );

    if (!token || !expirationDate) {
      return;
    } else {
      return {
        token,
        expirationDate: new Date(expirationDate),
        currentUser
      };
    }
  }
}

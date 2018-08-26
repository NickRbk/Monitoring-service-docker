import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from './shared/service/auth.service';
import {Customer} from './shared/model/customer.model';
import {Subscription} from 'rxjs';
import {CustomerService} from './shared/service/customer.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  currentUser: Customer;
  private currentUserSub: Subscription;

  constructor(private authenticatedStoreService: AuthService,
              private customerService: CustomerService) {}

  ngOnInit() {
    this.currentUser = this.customerService.getCurrentUser();
    this.customerService
      .currentUserListener
      .subscribe(customer => this.currentUser = customer);

    this.authenticatedStoreService.autoAuthUser();
  }

  ngOnDestroy() {
    this.currentUserSub.unsubscribe();
  }
}

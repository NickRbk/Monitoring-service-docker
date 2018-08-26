import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Customer} from '../../shared/model/customer.model';
import {AuthService} from '../../shared/service/auth.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent implements OnInit, OnDestroy {
  userIsAuthenticated = false;
  private authStatusSub: Subscription;

  @Output() closeSideNav = new EventEmitter<void>();
  @Input() currentUser: Customer;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.userIsAuthenticated = this.authService.getIsAuth();
    this.authStatusSub = this.authService
      .getAuthStatusListener()
      .subscribe(isAuthenticated => this.userIsAuthenticated = isAuthenticated);
  }

  ngOnDestroy() {
    this.authStatusSub.unsubscribe();
  }

  logout() {
    this.onClose();
    this.authService.logout();
  }

  onClose() {
    this.closeSideNav.emit();
  }
}

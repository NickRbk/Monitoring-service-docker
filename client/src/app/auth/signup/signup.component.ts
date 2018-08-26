import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../shared/service/auth.service';
import {ErrorService} from '../../shared/service/error.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Customer} from '../../shared/model/customer.model';
import {CustomerService} from '../../shared/service/customer.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit, OnDestroy {
  editMode = false;

  currentUser: Customer;
  onLoading = false;
  signupForm: FormGroup;
  private errorSub: Subscription;
  error = '';

  constructor(private authService: AuthService,
              private customerService: CustomerService,
              private router: Router,
              private route: ActivatedRoute,
              private errorService: ErrorService) { }

  ngOnInit() {
    this.errorSub = this.errorService.errorListener
      .subscribe(error => {
        this.onLoading = false;
        this.error = error;
      });

    this.route.params
      .subscribe(
        (params: Params) => {
          this.editMode = params['id'] != null;

          if (this.editMode) {
            this.currentUser = this.customerService.getCurrentUser();
          }

          this.initForm();
        }
      );
  }

  private initForm() {
    if (this.editMode && this.currentUser) {
      this.signupForm = new FormGroup({
        'firstName': new FormControl(this.currentUser.firstName, [Validators.required]),
        'lastName': new FormControl(this.currentUser.lastName, [Validators.required]),
        'email': new FormControl(this.currentUser.email, [Validators.required, Validators.email]),
        'password': new FormControl(null, [Validators.required, Validators.minLength(6)]),
        'phoneNumber': new FormControl(this.currentUser.phoneNumber, [Validators.required, Validators.minLength(10)])
      });
    } else {
      this.signupForm = new FormGroup({
        'firstName': new FormControl(null, [Validators.required]),
        'lastName': new FormControl(null, [Validators.required]),
        'email': new FormControl(null, [Validators.required, Validators.email]),
        'password': new FormControl(null, [Validators.required, Validators.minLength(6)]),
        'phoneNumber': new FormControl(null, [Validators.required, Validators.minLength(10)]),
        'agree': new FormControl(null, [Validators.required])
      });
    }
  }

  onCancel() {
    this.router.navigate(['/objectives']);
  }

  onRemove() {
    this.authService.deleteCustomer();
  }

  onSubmit() {
    this.onLoading = true;
    if (this.editMode) {
      this.authService.updateCustomer(this.signupForm.value);
    } else {
      this.authService.signUp(this.signupForm.value);
    }
  }

  ngOnDestroy() {
    this.errorSub.unsubscribe();
  }
}

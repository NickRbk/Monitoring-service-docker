import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ObjectivesService} from '../../shared/service/objectives.service';
import {Subscription} from 'rxjs';
import {SocialMediaService} from '../../shared/service/social-media.service';
import {ErrorService} from '../../shared/service/error.service';

@Component({
  selector: 'app-edit-objective',
  templateUrl: './edit-objective.component.html',
  styleUrls: ['./edit-objective.component.css']
})
export class EditObjectiveComponent implements OnInit, OnDestroy {
  objectiveForm: FormGroup;
  onSaving = false;

  id: number;
  editMode = false;
  objective;
  private errorSub: Subscription;

  error = '';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private errorService: ErrorService,
              private socialMediaService: SocialMediaService,
              private objectivesService: ObjectivesService) {
  }

  ngOnInit() {
    this.errorSub = this.errorService.errorListener
      .subscribe(error => this.error = error);

    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = params['id'];
          this.editMode = params['id'] != null;

          if (this.editMode) {
            this.objective = this.objectivesService.getObjective();
          }

          this.initForm();
        }
      );
  }

  ngOnDestroy() {
    this.errorSub.unsubscribe();
  }

  private initForm() {
    let firstName = '';
    let lastName = '';
    let alias = '';

    if (this.editMode && this.objective) {
      firstName = this.objective['firstName'];
      lastName = this.objective['lastName'];
      alias = this.objective['socialMedia']['twitter']['profile']['alias'];
    }

    this.objectiveForm = new FormGroup({
      'firstName': new FormControl(firstName, Validators.required),
      'lastName': new FormControl(lastName, Validators.required),
      'alias': new FormControl(alias, Validators.required)
    });
  }

  onSubmit() {
    const {firstName, lastName, alias} = this.objectiveForm.value;
    this.onSaving = true;

    this.objectivesService.checkAlias(alias.toLowerCase())
      .then(() => {
        if (this.editMode) {
          this.objectivesService.update(this.id, {firstName, lastName})
            .then(() => {
              this.socialMediaService.updateTwitterProfile(this.id, alias.toLowerCase())
                .then(() => this.onSuccessSubmit());
            });
        } else {
          this.objectivesService.save({firstName, lastName})
            .then((id: number) => {
              this.socialMediaService.updateTwitterProfile(id, alias.toLowerCase())
                .then(() => this.onSuccessSubmit());
            });
        }
      })
      .catch(() => {
        this.onSaving = false;
        this.errorService.triggerErrorMessage('ALIAS NOT FOUND');
      });
  }

  private onSuccessSubmit() {
    this.objectivesService.getObjectives();
    this.router.navigate(['/objectives']);
  }

  onCancel() {
    this.router.navigate(['/objectives']);
  }

  changeSaveButtonLabel() {
    return this.editMode ? 'Update' : 'Save';
  }

  onRemove() {
    this.objectivesService.delete(this.id);
    this.objectivesService.getObjectives();
  }
}

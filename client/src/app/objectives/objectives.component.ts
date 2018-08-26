import {Component, OnDestroy, OnInit} from '@angular/core';
import {ObjectivesService} from '../shared/service/objectives.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-objectives',
  templateUrl: './objectives.component.html',
  styleUrls: ['./objectives.component.css']
})

export class ObjectivesComponent implements OnInit, OnDestroy {
  loading = true;
  objectives;
  private objectivesSub: Subscription;

  constructor(private objectivesService: ObjectivesService) { }

  ngOnInit() {
    this.objectivesService.getObjectives();
    this.objectivesSub = this.objectivesService
      .getObjectivesListener()
      .subscribe(objectives => {
        this.objectives = objectives;
        this.loading = false;
      });
  }

  shareObjective(objective) {
    this.objectivesService.setObjective(objective);
  }

  ngOnDestroy() {
    // this.objectivesSub.unsubscribe();
  }
}

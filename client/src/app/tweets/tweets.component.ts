import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material';
import {TweetService} from '../shared/service/tweet.service';
import {ErrorService} from '../shared/service/error.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {SortCriteria} from '../shared/model/sort-criteria.model';

@Component({
  selector: 'app-tweets',
  templateUrl: './tweets.component.html',
  styleUrls: ['./tweets.component.css']
})
export class TweetsComponent implements OnInit, OnDestroy {

  onLoading = true;
  private errorSub: Subscription;

  page = 0;
  size = 5;
  orderBy: string;
  direction = 'asc';

  pageSizeOptions: number[] = [5, 10, 15, 20];

  sortCriteriaOptions: SortCriteria[] = [
    {value: 'date', viewValue: 'tweet\'s date'},
    {value: 'fav', viewValue: 'favourites'},
    {value: 'share', viewValue: 'share'}
  ];

  directionOptions: string[] = ['desc', 'asc'];

  totalCount: number;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  tweets: [any];

  error = '';

  constructor(private tweetService: TweetService,
              private route: ActivatedRoute,
              private router: Router,
              private errorService: ErrorService) { }

  ngOnInit() {
    this.errorSub = this.errorService.errorListener
      .subscribe(error => this.error = error);

    this.fetchTweets(this.page, this.size, this.orderBy, this.direction);
  }

  onPaginator(pageEvent: PageEvent) {
    this.page = pageEvent['pageIndex'];
    this.size = pageEvent['pageSize'];

    this.fetchTweets(this.page, this.size, this.orderBy, this.direction);
  }

  onSortSelector() {
    this.fetchTweets(0, this.size, this.orderBy, this.direction);
  }

  private fetchTweets(page: number, size: number, orderBy: string, direction: string) {
    this.onLoading = true;

    document.querySelector('#header').scrollIntoView();
    this.tweetService.fetchTweets(page, size, orderBy, direction)
      .then(
        res => {
          this.totalCount = res['totalElements'];
          this.tweets = res['content'];
          this.onLoading = false;
        }
      );
  }

  ngOnDestroy() {
    this.errorSub.unsubscribe();
  }
}

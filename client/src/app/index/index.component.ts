import {Component, OnInit} from '@angular/core';
import {NavService} from '../nav/nav.service';
import {Route, Router} from '@angular/router';
import {TimelineFilterBarChartComponent} from '../chart/chart.component';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    controllers: Array<any>;
    serverUrl: string;
    width = 900;
    height = 500;
    type = 'msline';
    data;
    // jsonurl and uncomment this to use by remote source
    //dataSource = environment.serverUrl + 'currency';
    dataFormat = 'json';

    constructor(private navService: NavService, private router: Router,private http: HttpClient) {

    }

    ngOnInit(): void {
        this.serverUrl = environment.serverUrl;
        this.http.get(this.serverUrl + 'currency').subscribe((res: Response) => {
            console.log(res);
            this.data=res;
        });
        this.navService.getNavData().subscribe(applicationData => {
            this.controllers = applicationData.controllers.sort((a: any, b: any) => {
                if (a.name < b.name) {
                    return -1;
                } else if (a.name > b.name) {
                    return 1;
                } else {
                    return 0;
                }
            });
        });
    }

    hasRoute(controllerName: string): boolean {
        return this.router.config.some((route: Route) => {
            if (route.path === controllerName) {
                return true;
            }
        });
    }
}

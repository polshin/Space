import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {IndexComponent} from './index/index.component';
import {TabModule} from 'angular-tabs-component'
import {TimelineFilterBarChartComponent} from './chart/chart.component';
import {HttpClientModule} from '@angular/common/http';

import { CommonModule } from '@angular/common';
import {AppComponent} from './app.component';
import {NavComponent} from './nav/nav.component';
import {NavService} from './nav/nav.service';
import {AppRoutingModule} from "./app-routing.module";
import * as FusionCharts from 'fusioncharts';
import * as Charts from 'fusioncharts/fusioncharts.charts';
import * as FintTheme from 'fusioncharts/themes/fusioncharts.theme.fint';
import { FusionChartsModule } from 'angular4-fusioncharts';

FusionChartsModule.fcRoot(FusionCharts, Charts, FintTheme);
@NgModule({
    declarations: [
        AppComponent,
        NavComponent,
        IndexComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        CommonModule,
        HttpClientModule,
        AppRoutingModule,
        BrowserModule,
        FusionChartsModule,
        TabModule,
        NgbModule.forRoot()
    ],
    providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}, NavService],
    bootstrap: [AppComponent]
})
export class AppModule {
}

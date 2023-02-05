import {Component, Input} from '@angular/core';
import {SearchBy} from "../../models/search-by";
import {FilterDto} from "../../models/filter-dto";

@Component({
  selector: 'app-show-last-searched-values',
  templateUrl: './show-last-searched-values.component.html',
  styleUrls: ['./show-last-searched-values.component.scss']
})
export class ShowLastSearchedValuesComponent {

  @Input() isSearched: boolean = true
  @Input() searchParams: SearchBy[] = []
  @Input() filterParams: FilterDto[] = []


}

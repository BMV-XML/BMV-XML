import {Injectable} from '@angular/core';

declare const Xonomy: any

@Injectable({
  providedIn: 'root'
})
export class XonomyService {

  constructor() {
  }

  public patentSpecification = {
    elements: {
      Naslovi: {
        displayName: "Naslovi",
        menu: [{
          caption: 'Dodaj naslov',
          action: Xonomy.newElementChild,
          actionParameter: '<Naslov jezik=""></Naslov>',
        }]
      },
      Naslov: {
        hasText: true,
        //asker: Xonomy.askString,
        inlineMenu: [{
          caption: "Boldujte",
          action: Xonomy.wrap,
          actionParameter: {template: "<b>$</b>", placeholder: "$"}
        },{
          caption: "Small",
          action: Xonomy.wrap,
          actionParameter: {template: "<small>$</small>", placeholder: "$"}
        },{
          caption: "Italic",
          action: Xonomy.wrap,
          actionParameter: {template: "<i>$</i>", placeholder: "$"}
        },{
          caption: "Mark",
          action: Xonomy.wrap,
          actionParameter: {template: "<mark>$</mark>", placeholder: "$"}
        },{
            caption: "insert",
            action: Xonomy.wrap,
            actionParameter: {template: "<ins>$</ins>", placeholder: "$"}
          }],

        menu: [{
          caption: 'Dodaj jezik',
          action: Xonomy.newAttribute,
          actionParameter: {name: 'jezik', value: 'f'},
          hideIf: function (jsElement: any) {
            return jsElement.hasAttribute("jezik");
          },
        }],
        attributes: {
          jezik: {
            asker: Xonomy.askPicklist,
            //askerParameter: ["United States", "Canada", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and/or Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France, Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States minor outlying islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zaire", "Zambia", "Zimbabwe"],
            askerParameter: [
              {"name": "Afghanistan", "value": "AF"},
              {"name": "Åland Islands", "value": "AX"},
              {"name": "Albania", "value": "AL"},
              {"name": "Algeria", "value": "DZ"},
              {"name": "American Samoa", "value": "AS"},
              {"name": "AndorrA", "value": "AD"},
              {"name": "Angola", "value": "AO"},
              {"name": "Anguilla", "value": "AI"},
              {"name": "Antarctica", "value": "AQ"},
              {"name": "Antigua and Barbuda", "value": "AG"},
              {"name": "Argentina", "value": "AR"},
              {"name": "Armenia", "value": "AM"},
              {"name": "Aruba", "value": "AW"},
              {"name": "Australia", "value": "AU"},
              {"name": "Austria", "value": "AT"},
              {"name": "Azerbaijan", "value": "AZ"},
              {"name": "Bahamas", "value": "BS"},
              {"name": "Bahrain", "value": "BH"},
              {"name": "Bangladesh", "value": "BD"},
              {"name": "Barbados", "value": "BB"},
              {"name": "Belarus", "value": "BY"},
              {"name": "Belgium", "value": "BE"},
              {"name": "Belize", "value": "BZ"},
              {"name": "Benin", "value": "BJ"},
              {"name": "Bermuda", "value": "BM"},
              {"name": "Bhutan", "value": "BT"},
              {"name": "Bolivia", "value": "BO"},
              {"name": "Bosnia and Herzegovina", "value": "BA"},
              {"name": "Botswana", "value": "BW"},
              {"name": "Bouvet Island", "value": "BV"},
              {"name": "Brazil", "value": "BR"},
              {"name": "British Indian Ocean Territory", "value": "IO"},
              {"name": "Brunei Darussalam", "value": "BN"},
              {"name": "Bulgaria", "value": "BG"},
              {"name": "Burkina Faso", "value": "BF"},
              {"name": "Burundi", "value": "BI"},
              {"name": "Cambodia", "value": "KH"},
              {"name": "Cameroon", "value": "CM"},
              {"name": "Canada", "value": "CA"},
              {"name": "Cape Verde", "value": "CV"},
              {"name": "Cayman Islands", "value": "KY"},
              {"name": "Central African Republic", "value": "CF"},
              {"name": "Chad", "value": "TD"},
              {"name": "Chile", "value": "CL"},
              {"name": "China", "value": "CN"},
              {"name": "Christmas Island", "value": "CX"},
              {"name": "Cocos (Keeling) Islands", "value": "CC"},
              {"name": "Colombia", "value": "CO"},
              {"name": "Comoros", "value": "KM"},
              {"name": "Congo", "value": "CG"},
              {"name": "Congo, The Democratic Republic of the", "value": "CD"},
              {"name": "Cook Islands", "value": "CK"},
              {"name": "Costa Rica", "value": "CR"},
              {"name": "Cote D\"Ivoire", "value": "CI"},
              {"name": "Croatia", "value": "HR"},
              {"name": "Cuba", "value": "CU"},
              {"name": "Cyprus", "value": "CY"},
              {"name": "Czech Republic", "value": "CZ"},
              {"name": "Denmark", "value": "DK"},
              {"name": "Djibouti", "value": "DJ"},
              {"name": "Dominica", "value": "DM"},
              {"name": "Dominican Republic", "value": "DO"},
              {"name": "Ecuador", "value": "EC"},
              {"name": "Egypt", "value": "EG"},
              {"name": "El Salvador", "value": "SV"},
              {"name": "Equatorial Guinea", "value": "GQ"},
              {"name": "Eritrea", "value": "ER"},
              {"name": "Estonia", "value": "EE"},
              {"name": "Ethiopia", "value": "ET"},
              {"name": "Falkland Islands (Malvinas)", "value": "FK"},
              {"name": "Faroe Islands", "value": "FO"},
              {"name": "Fiji", "value": "FJ"},
              {"name": "Finland", "value": "FI"},
              {"name": "France", "value": "FR"},
              {"name": "French Guiana", "value": "GF"},
              {"name": "French Polynesia", "value": "PF"},
              {"name": "French Southern Territories", "value": "TF"},
              {"name": "Gabon", "value": "GA"},
              {"name": "Gambia", "value": "GM"},
              {"name": "Georgia", "value": "GE"},
              {"name": "Germany", "value": "DE"},
              {"name": "Ghana", "value": "GH"},
              {"name": "Gibraltar", "value": "GI"},
              {"name": "Greece", "value": "GR"},
              {"name": "Greenland", "value": "GL"},
              {"name": "Grenada", "value": "GD"},
              {"name": "Guadeloupe", "value": "GP"},
              {"name": "Guam", "value": "GU"},
              {"name": "Guatemala", "value": "GT"},
              {"name": "Guernsey", "value": "GG"},
              {"name": "Guinea", "value": "GN"},
              {"name": "Guinea-Bissau", "value": "GW"},
              {"name": "Guyana", "value": "GY"},
              {"name": "Haiti", "value": "HT"},
              {"name": "Heard Island and Mcdonald Islands", "value": "HM"},
              {"name": "Holy See (Vatican City State)", "value": "VA"},
              {"name": "Honduras", "value": "HN"},
              {"name": "Hong Kong", "value": "HK"},
              {"name": "Hungary", "value": "HU"},
              {"name": "Iceland", "value": "IS"},
              {"name": "India", "value": "IN"},
              {"name": "Indonesia", "value": "ID"},
              {"name": "Iran, Islamic Republic Of", "value": "IR"},
              {"name": "Iraq", "value": "IQ"},
              {"name": "Ireland", "value": "IE"},
              {"name": "Isle of Man", "value": "IM"},
              {"name": "Israel", "value": "IL"},
              {"name": "Italy", "value": "IT"},
              {"name": "Jamaica", "value": "JM"},
              {"name": "Japan", "value": "JP"},
              {"name": "Jersey", "value": "JE"},
              {"name": "Jordan", "value": "JO"},
              {"name": "Kazakhstan", "value": "KZ"},
              {"name": "Kenya", "value": "KE"},
              {"name": "Kiribati", "value": "KI"},
              {"name": "Korea, Democratic People\"S Republic of", "value": "KP"},
              {"name": "Korea, Republic of", "value": "KR"},
              {"name": "Kuwait", "value": "KW"},
              {"name": "Kyrgyzstan", "value": "KG"},
              {"name": "Lao People\"S Democratic Republic", "value": "LA"},
              {"name": "Latvia", "value": "LV"},
              {"name": "Lebanon", "value": "LB"},
              {"name": "Lesotho", "value": "LS"},
              {"name": "Liberia", "value": "LR"},
              {"name": "Libyan Arab Jamahiriya", "value": "LY"},
              {"name": "Liechtenstein", "value": "LI"},
              {"name": "Lithuania", "value": "LT"},
              {"name": "Luxembourg", "value": "LU"},
              {"name": "Macao", "value": "MO"},
              {"name": "Macedonia, The Former Yugoslav Republic of", "value": "MK"},
              {"name": "Madagascar", "value": "MG"},
              {"name": "Malawi", "value": "MW"},
              {"name": "Malaysia", "value": "MY"},
              {"name": "Maldives", "value": "MV"},
              {"name": "Mali", "value": "ML"},
              {"name": "Malta", "value": "MT"},
              {"name": "Marshall Islands", "value": "MH"},
              {"name": "Martinique", "value": "MQ"},
              {"name": "Mauritania", "value": "MR"},
              {"name": "Mauritius", "value": "MU"},
              {"name": "Mayotte", "value": "YT"},
              {"name": "Mexico", "value": "MX"},
              {"name": "Micronesia, Federated States of", "value": "FM"},
              {"name": "Moldova, Republic of", "value": "MD"},
              {"name": "Monaco", "value": "MC"},
              {"name": "Mongolia", "value": "MN"},
              {"name": "Montserrat", "value": "MS"},
              {"name": "Morocco", "value": "MA"},
              {"name": "Mozambique", "value": "MZ"},
              {"name": "Myanmar", "value": "MM"},
              {"name": "Namibia", "value": "NA"},
              {"name": "Nauru", "value": "NR"},
              {"name": "Nepal", "value": "NP"},
              {"name": "Netherlands", "value": "NL"},
              {"name": "Netherlands Antilles", "value": "AN"},
              {"name": "New Caledonia", "value": "NC"},
              {"name": "New Zealand", "value": "NZ"},
              {"name": "Nicaragua", "value": "NI"},
              {"name": "Niger", "value": "NE"},
              {"name": "Nigeria", "value": "NG"},
              {"name": "Niue", "value": "NU"},
              {"name": "Norfolk Island", "value": "NF"},
              {"name": "Northern Mariana Islands", "value": "MP"},
              {"name": "Norway", "value": "NO"},
              {"name": "Oman", "value": "OM"},
              {"name": "Pakistan", "value": "PK"},
              {"name": "Palau", "value": "PW"},
              {"name": "Palestinian Territory, Occupied", "value": "PS"},
              {"name": "Panama", "value": "PA"},
              {"name": "Papua New Guinea", "value": "PG"},
              {"name": "Paraguay", "value": "PY"},
              {"name": "Peru", "value": "PE"},
              {"name": "Philippines", "value": "PH"},
              {"name": "Pitcairn", "value": "PN"},
              {"name": "Poland", "value": "PL"},
              {"name": "Portugal", "value": "PT"},
              {"name": "Puerto Rico", "value": "PR"},
              {"name": "Qatar", "value": "QA"},
              {"name": "Reunion", "value": "RE"},
              {"name": "Romania", "value": "RO"},
              {"name": "Russian Federation", "value": "RU"},
              {"name": "RWANDA", "value": "RW"},
              {"name": "Saint Helena", "value": "SH"},
              {"name": "Saint Kitts and Nevis", "value": "KN"},
              {"name": "Saint Lucia", "value": "LC"},
              {"name": "Saint Pierre and Miquelon", "value": "PM"},
              {"name": "Saint Vincent and the Grenadines", "value": "VC"},
              {"name": "Samoa", "value": "WS"},
              {"name": "San Marino", "value": "SM"},
              {"name": "Sao Tome and Principe", "value": "ST"},
              {"name": "Saudi Arabia", "value": "SA"},
              {"name": "Senegal", "value": "SN"},
              {"name": "Serbia and Montenegro", "value": "CS"},
              {"name": "Seychelles", "value": "SC"},
              {"name": "Sierra Leone", "value": "SL"},
              {"name": "Singapore", "value": "SG"},
              {"name": "Slovakia", "value": "SK"},
              {"name": "Slovenia", "value": "SI"},
              {"name": "Solomon Islands", "value": "SB"},
              {"name": "Somalia", "value": "SO"},
              {"name": "South Africa", "value": "ZA"},
              {"name": "South Georgia and the South Sandwich Islands", "value": "GS"},
              {"name": "Spain", "value": "ES"},
              {"name": "Sri Lanka", "value": "LK"},
              {"name": "Sudan", "value": "SD"},
              {"name": "Suriname", "value": "SR"},
              {"name": "Svalbard and Jan Mayen", "value": "SJ"},
              {"name": "Swaziland", "value": "SZ"},
              {"name": "Sweden", "value": "SE"},
              {"name": "Switzerland", "value": "CH"},
              {"name": "Syrian Arab Republic", "value": "SY"},
              {"name": "Taiwan", "value": "TW"},
              {"name": "Tajikistan", "value": "TJ"},
              {"name": "Tanzania, United Republic of", "value": "TZ"},
              {"name": "Thailand", "value": "TH"},
              {"name": "Timor-Leste", "value": "TL"},
              {"name": "Togo", "value": "TG"},
              {"name": "Tokelau", "value": "TK"},
              {"name": "Tonga", "value": "TO"},
              {"name": "Trinidad and Tobago", "value": "TT"},
              {"name": "Tunisia", "value": "TN"},
              {"name": "Turkey", "value": "TR"},
              {"name": "Turkmenistan", "value": "TM"},
              {"name": "Turks and Caicos Islands", "value": "TC"},
              {"name": "Tuvalu", "value": "TV"},
              {"name": "Uganda", "value": "UG"},
              {"name": "Ukraine", "value": "UA"},
              {"name": "United Arab Emirates", "value": "AE"},
              {"name": "United Kingdom", "value": "GB"},
              {"name": "United States", "value": "US"},
              {"name": "United States Minor Outlying Islands", "value": "UM"},
              {"name": "Uruguay", "value": "UY"},
              {"name": "Uzbekistan", "value": "UZ"},
              {"name": "Vanuatu", "value": "VU"},
              {"name": "Venezuela", "value": "VE"},
              {"name": "Viet Nam", "value": "VN"},
              {"name": "Virgin Islands, British", "value": "VG"},
              {"name": "Virgin Islands, U.S.", "value": "VI"},
              {"name": "Wallis and Futuna", "value": "WF"},
              {"name": "Western Sahara", "value": "EH"},
              {"name": "Yemen", "value": "YE"},
              {"name": "Zambia", "value": "ZM"},
              {"name": "Zimbabwe", "value": "ZW"}
            ],
            //asker: Xonomy.askRemote,
            //askerParameter: 'api/lists/value',
            menu: [{
              caption: "Obrisi me",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },

      b: {
        hasText: true,
        menu: [{
          caption: "Unboldujte",
          action: Xonomy.unwrap
        }]
      },

      small: {
        hasText: true,
        menu: [{
          caption: "Unsmallujte",
          action: Xonomy.unwrap
        }]
      },

      i: {
        hasText: true,
        menu: [{
          caption: "Unitalikujte",
          action: Xonomy.unwrap
        }]
      },

      mark: {
        hasText: true,
        menu: [{
          caption: "Unmarkujte",
          action: Xonomy.unwrap
        }]
      },

      ins: {
        hasText: true,
        menu: [{
          caption: "Uninsujte",
          action: Xonomy.unwrap
        }]
      }

    }
  }

}

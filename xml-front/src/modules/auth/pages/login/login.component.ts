import {Component} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {LoginDto} from "../../models/login-dto";
import * as xml2js from "xml2js";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OFFICIAL, PATENT, STAMP} from "../../types";
import {MessageService} from "primeng/api";
import {MatRadioChange} from "@angular/material/radio";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService]
})
export class LoginComponent {
  loginForm: FormGroup
  usernameFormControl = new FormControl('', [Validators.required])
  passwordFormControl = new FormControl('', [Validators.required])
  chosenRole: string = "PATENT"

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private router: Router,
    private readonly messageService: MessageService,
  ) {
    this.loginForm = this.fb.group({
      username: this.usernameFormControl,
      password: this.passwordFormControl
    })
  }


  ngOnInit() {

    this.loginForm.valueChanges.subscribe()
  }

  login() {
    if (!this.loginForm)
      return
    let loginObj: LoginDto = {
      username: this.usernameFormControl.value,
      password: this.passwordFormControl.value,
      service: this.chosenRole
    }
    this.authService.login(loginObj).subscribe(
      (res) => {
        //var xml = "<config><test>Hello</test><data>SomeData</data></config>";
        console.log("**********************")
        console.log(res)
        var parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          if (result['AuthTypeDTO']['successful'][0] === 'true') {
            this.authService.saveCredentialsAndType(loginObj, result['AuthTypeDTO']['type'][0])
            if (result['AuthTypeDTO']['type'][0] === PATENT)
              this.router.navigateByUrl("patent/add")
            else if (result['AuthTypeDTO']['type'][0] === OFFICIAL)
              this.router.navigateByUrl("patent/list")
            else if (result['AuthTypeDTO']['type'][0] === STAMP){
              this.router.navigateByUrl("stamp/add")
            }
          } else {
            this.messageService.add({
              key: 'login-message',
              severity: 'warn',
              summary: 'Neuspešno logovanje',
              detail: 'Korisničko ime ili lozinka nije ispravna.'
            })
          }
        });
      },
      (err) => {
        console.log("------------------------------------------")
        console.log(err)
      }
    )
  }

  optionChanged($event: MatRadioChange): void {
    if ($event.value === '1')
      this.chosenRole = "PATENT"
    else if ($event.value === '2')
      this.chosenRole = "STAMP"
  }
}

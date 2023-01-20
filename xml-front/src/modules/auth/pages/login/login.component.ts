import {Component} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {LoginDto} from "../../models/login-dto";
import * as xml2js from "xml2js";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OFFICIAL, PATENT} from "../../types";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup
  usernameFormControl = new FormControl('', [Validators.required])
  passwordFormControl = new FormControl('', [Validators.required])

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private router: Router
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
      password: this.passwordFormControl.value
    }
    this.authService.login(loginObj).subscribe(
      (res) => {
        //var xml = "<config><test>Hello</test><data>SomeData</data></config>";
        console.log("**********************")
        console.log(res)
        var parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          if (result['AuthTypeDTO']['successful'][0]) {
            this.authService.saveCredentialsAndType(loginObj, result['AuthTypeDTO']['type'][0])
            if (result['AuthTypeDTO']['type'][0] === PATENT)
              this.router.navigateByUrl("patent/add")
            else if (result['AuthTypeDTO']['type'][0] === OFFICIAL)
              this.router.navigateByUrl("patent/list")
          }
        });
      },
      (err) => {
        console.log("------------------------------------------")
        console.log(err)
      }
    )
  }
}

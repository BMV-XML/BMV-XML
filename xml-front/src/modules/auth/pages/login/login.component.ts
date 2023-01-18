import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {LoginDto} from "../../models/login-dto";
import * as xml2js from "xml2js";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup
  usernameFormControl = new FormControl('', [Validators.required])
  passwordFormControl = new FormControl('', [Validators.required])

  constructor (
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: this.usernameFormControl,
      password: this.passwordFormControl
    })
  }


  ngOnInit () {

    this.loginForm.valueChanges.subscribe()
  }

  login() {
    if (!this.loginForm)
      return
    let loginObj : LoginDto = {
      username: this.usernameFormControl.value,
      password: this.passwordFormControl.value
    }
    this.authService.login(loginObj).subscribe(
      (res) => {
        //var xml = "<config><test>Hello</test><data>SomeData</data></config>";
        console.log("**********************")
        console.log(res)
        var extractedData = "";
        var parser = new xml2js.Parser();
        parser.parseString(res, (err,result) =>{
          //Extract the value from the data element
         // extractedData = result['config']['data']
          console.log("**********************")
          if (result['AuthTypeDTO']['successful'][0] && result['AuthTypeDTO']['type'][0])
            this.router.navigateByUrl("patent/add")
          console.log(result['AuthTypeDTO']['type'][0]);
        });
      },
      (err) => {
        console.log("------------------------------------------")
        console.log(err)
      }
    )
  }
}

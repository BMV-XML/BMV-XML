import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {LoginDto} from "../../models/login-dto";
import * as xml2js from "xml2js";
import {RegisterDto} from "../../models/register-dto";
import {MatRadioChange} from "@angular/material/radio";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup
  usernameFormControl = new FormControl('', [Validators.required])
  passwordFormControl = new FormControl('', [Validators.required])
  chosenRole: string = "PATENT"

  constructor (
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: this.usernameFormControl,
      password: this.passwordFormControl
    })
  }


  ngOnInit () {

    this.registerForm.valueChanges.subscribe()
  }

  register() {
    if (!this.registerForm)
      return
    let registerDto : RegisterDto = {
      service: this.chosenRole,
      username: this.usernameFormControl.value,
      password: this.passwordFormControl.value
    }
    this.authService.register(registerDto).subscribe(
      (res) => {
        //var xml = "<config><test>Hello</test><data>SomeData</data></config>";
        console.log("**********************")
        console.log(res)
        var parser = new xml2js.Parser();
        parser.parseString(res, (err,result) =>{
          console.log("**********************")
          console.log(result)
          if (result['SuccessDTO']['successful'][0])
            this.router.navigateByUrl("auth/login")
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

import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import * as xml2js from "xml2js";
import {RegisterDto} from "../../models/register-dto";
import {MatRadioChange} from "@angular/material/radio";
import {MessageService} from "primeng/api";
import {PATENT, STAMP} from "../../types";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  providers: [MessageService]
})
export class RegisterComponent {
  registerForm: FormGroup
  capitalFirstLetterText = Validators.pattern('[A-ZŠĆČĐŽ][a-zšđčćž]*')
  username = Validators.pattern('[a-zšđčćž0-9]*')
  usernameFormControl = new FormControl('', [Validators.required, this.username])
  passwordFormControl = new FormControl('', [Validators.required])
  nameFormControl = new FormControl('', [Validators.required, this.capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, this.capitalFirstLetterText])
  chosenRole: string = PATENT

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private router: Router,
    private readonly messageService: MessageService,
  ) {
    this.registerForm = this.fb.group({
      username: this.usernameFormControl,
      password: this.passwordFormControl,
      name: this.nameFormControl,
      surname: this.surnameFormControl
    })
  }


  ngOnInit() {

    this.registerForm.valueChanges.subscribe()
  }

  register() {
    if (!this.registerForm)
      return
    let registerDto: RegisterDto = {
      service: this.chosenRole,
      username: this.usernameFormControl.value,
      password: this.passwordFormControl.value,
      name: this.nameFormControl.value,
      surname: this.surnameFormControl.value,
    }
    this.authService.register(registerDto).subscribe(
      (res) => {
        //var xml = "<config><test>Hello</test><data>SomeData</data></config>";
        console.log("**********************")
        console.log(res)
        var parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          console.log("**********************")
          console.log(result)
          if (result['SuccessDTO']['successful'][0]) {
            this.messageService.add({
              key: 'registration-message',
              severity: 'success',
              summary: 'Uspešno ste se registrovalo',
              detail: 'Molimo Vas pre nastavka ulogujte se.'
            })
            setTimeout(()=> {
              this.router.navigateByUrl("auth/login")
            }, 2000)
          } else {
            this.messageService.add({
              key: 'registration-message',
              severity: 'warn',
              summary: 'Neuspešna registracija',
              detail: 'Nismo uspeli da Vas registrujemo.'
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
      this.chosenRole = PATENT
    else if ($event.value === '2')
      this.chosenRole = STAMP
    else
      this.chosenRole = "AUTHORSHIP"
  }
}

import {Validators} from "@angular/forms";

export const emailValidator = Validators.email
export const capitalFirstLetterText = Validators.pattern('[A-ZŠĆČĐŽ][a-zšđčćž]*')
export const capitalFirstLetterTextWithSpace = Validators.pattern('[A-ZŠĆČĐŽ][a-zšđčćžA-ZŠĆČĐŽ\\s]*');
export const postalNumber = Validators.pattern('[0-9]{5}')
export const stringValidator = Validators.pattern('[A-ZŠĆČĐŽa-zšđčćž][a-zšđčćž]*')
export const stringAndNumber = Validators.pattern('[A-ZŠĆČĐŽa-zšđčćž0-9][a-zšđčćž\\s0-9]*')
export const phoneValidator = Validators.pattern('[\+][0-9]{6,13}')
export const numberValidator = Validators.pattern('[0-9]{6, 12}')
export const applicationNumberValidator = Validators.pattern('P-[0-9]{4,10}/[0-9]{2}')

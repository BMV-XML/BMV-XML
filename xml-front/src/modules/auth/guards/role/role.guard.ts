import { Injectable } from '@angular/core'
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router'
@Injectable({
  providedIn: 'root'
})
// admin
// registeredUser
// driver
export class RoleGuard implements CanActivate {
  constructor ( public router: Router) {}

  canActivate (route: ActivatedRouteSnapshot): boolean {
    const expectedRole: string = route.data['expectedRole']

    //const roles: string[] = expectedRoles.split('|', 3)
    if (localStorage.getItem('type') === expectedRole) {
      { return true }
    }
    this.router.navigate(['/auth/login'])
    return false
  }
}

import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GrupoComponentsPage, GrupoUpdatePage } from './grupo.page-object';

describe('Grupo e2e test', () => {
    let navBarPage: NavBarPage;
    let grupoUpdatePage: GrupoUpdatePage;
    let grupoComponentsPage: GrupoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Grupos', () => {
        navBarPage.goToEntity('grupo');
        grupoComponentsPage = new GrupoComponentsPage();
        expect(grupoComponentsPage.getTitle()).toMatch(/adminhouseApp.grupo.home.title/);
    });

    it('should load create Grupo page', () => {
        grupoComponentsPage.clickOnCreateButton();
        grupoUpdatePage = new GrupoUpdatePage();
        expect(grupoUpdatePage.getPageTitle()).toMatch(/adminhouseApp.grupo.home.createOrEditLabel/);
        grupoUpdatePage.cancel();
    });

    it('should create and save Grupos', () => {
        grupoComponentsPage.clickOnCreateButton();
        // grupoUpdatePage.usersSelectLastOption();
        grupoUpdatePage.save();
        expect(grupoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

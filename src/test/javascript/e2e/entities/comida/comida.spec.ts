import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ComidaComponentsPage, ComidaUpdatePage } from './comida.page-object';

describe('Comida e2e test', () => {
    let navBarPage: NavBarPage;
    let comidaUpdatePage: ComidaUpdatePage;
    let comidaComponentsPage: ComidaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Comidas', () => {
        navBarPage.goToEntity('comida');
        comidaComponentsPage = new ComidaComponentsPage();
        expect(comidaComponentsPage.getTitle()).toMatch(/adminhouseApp.comida.home.title/);
    });

    it('should load create Comida page', () => {
        comidaComponentsPage.clickOnCreateButton();
        comidaUpdatePage = new ComidaUpdatePage();
        expect(comidaUpdatePage.getPageTitle()).toMatch(/adminhouseApp.comida.home.createOrEditLabel/);
        comidaUpdatePage.cancel();
    });

    it('should create and save Comidas', () => {
        comidaComponentsPage.clickOnCreateButton();
        comidaUpdatePage.setNombreInput('nombre');
        expect(comidaUpdatePage.getNombreInput()).toMatch('nombre');
        comidaUpdatePage.save();
        expect(comidaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

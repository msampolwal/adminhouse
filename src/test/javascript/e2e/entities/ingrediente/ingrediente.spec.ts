import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { IngredienteComponentsPage, IngredienteUpdatePage } from './ingrediente.page-object';

describe('Ingrediente e2e test', () => {
    let navBarPage: NavBarPage;
    let ingredienteUpdatePage: IngredienteUpdatePage;
    let ingredienteComponentsPage: IngredienteComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ingredientes', () => {
        navBarPage.goToEntity('ingrediente');
        ingredienteComponentsPage = new IngredienteComponentsPage();
        expect(ingredienteComponentsPage.getTitle()).toMatch(/adminhouseApp.ingrediente.home.title/);
    });

    it('should load create Ingrediente page', () => {
        ingredienteComponentsPage.clickOnCreateButton();
        ingredienteUpdatePage = new IngredienteUpdatePage();
        expect(ingredienteUpdatePage.getPageTitle()).toMatch(/adminhouseApp.ingrediente.home.createOrEditLabel/);
        ingredienteUpdatePage.cancel();
    });

    it('should create and save Ingredientes', () => {
        ingredienteComponentsPage.clickOnCreateButton();
        ingredienteUpdatePage.setCantidadInput('5');
        expect(ingredienteUpdatePage.getCantidadInput()).toMatch('5');
        ingredienteUpdatePage.comidaSelectLastOption();
        ingredienteUpdatePage.productoSelectLastOption();
        ingredienteUpdatePage.save();
        expect(ingredienteUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { PrecioProductoComponentsPage, PrecioProductoUpdatePage } from './precio-producto.page-object';

describe('PrecioProducto e2e test', () => {
    let navBarPage: NavBarPage;
    let precioProductoUpdatePage: PrecioProductoUpdatePage;
    let precioProductoComponentsPage: PrecioProductoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PrecioProductos', () => {
        navBarPage.goToEntity('precio-producto');
        precioProductoComponentsPage = new PrecioProductoComponentsPage();
        expect(precioProductoComponentsPage.getTitle()).toMatch(/adminhouseApp.precioProducto.home.title/);
    });

    it('should load create PrecioProducto page', () => {
        precioProductoComponentsPage.clickOnCreateButton();
        precioProductoUpdatePage = new PrecioProductoUpdatePage();
        expect(precioProductoUpdatePage.getPageTitle()).toMatch(/adminhouseApp.precioProducto.home.createOrEditLabel/);
        precioProductoUpdatePage.cancel();
    });

    it('should create and save PrecioProductos', () => {
        precioProductoComponentsPage.clickOnCreateButton();
        precioProductoUpdatePage.setPrecioInput('5');
        expect(precioProductoUpdatePage.getPrecioInput()).toMatch('5');
        precioProductoUpdatePage.setCantidadInput('5');
        expect(precioProductoUpdatePage.getCantidadInput()).toMatch('5');
        precioProductoUpdatePage.productoSelectLastOption();
        precioProductoUpdatePage.unidadMedidaSelectLastOption();
        precioProductoUpdatePage.save();
        expect(precioProductoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

/**
 * PrimeFaces California Layout
 */
PrimeFaces.widget.California = PrimeFaces.widget.BaseWidget.extend({

    init: function (cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        this.topbar = this.wrapper.children('.layout-topbar');
        this.sidebar = this.wrapper.children('.layout-sidebar');
        this.layoutMegaMenu = $('#layout-megamenu');
        this.nanoContainer = $('#nano-container');
        this.menu = this.jq;
        this.menulinks = this.menu.find('a');
        this.expandedMenuitems = this.expandedMenuitems || [];
        this.menuButton = $('#menu-button');
        this.megaMenuButton = $('#layout-megamenu-button');
        this.topbarMenuButton = $('#topbar-menu-button');
        this.sidebarProfileButton = $('#sidebar-profile-button');

        this.sidebarUserMenu = $('#sidebar-usermenu');
        this.topbarUserMenu = $('#topbar-usermenu');

        if (this.sidebarUserMenu.length)
            this.usermenuLinks = this.sidebarUserMenu.find('a');
        if (this.topbarUserMenu.length)
            this.usermenuLinks = this.topbarUserMenu.find('a');

        this.topbarMenuClick = false;
        this.megaMenuClick = false;
        this.rightSidebarClick = false;
        this.sidebarMenuClick = false;

        this._bindEvents();

        if (!this.wrapper.hasClass('menu-layout-horizontal')) {
            this.restoreMenuState();
        }

        this.nanoContainer.nanoScroller({flash: true});
    },

    _bindEvents: function () {
        var $this = this;

        $(function () {
            $this.menuButton.off('click').on('click', function (e) {
                $this.sidebarMenuClick = true;

                if ($this.isDesktop()) {
                    if ($this.isOverlay())
                        $this.wrapper.toggleClass('layout-wrapper-overlay-sidebar-active');
                    else
                        $this.wrapper.toggleClass('layout-wrapper-sidebar-inactive');
                }
                else {
                    $this.wrapper.toggleClass('layout-wrapper-sidebar-mobile-active');
                }

                e.preventDefault();
            });

            $this.megaMenuButton.off('click').on('click', function (e) {
                $this.megaMenuClick = true;

                if ($this.layoutMegaMenu.hasClass('layout-megamenu-active')) {
                    $this.layoutMegaMenu.removeClass('fadeInDown').addClass('fadeOutUp');

                    setTimeout(function () {
                        $this.layoutMegaMenu.removeClass('layout-megamenu-active fadeOutUp');
                        $(document.body).removeClass('body-megamenu-active');
                    }, 250);
                }
                else {
                    $(document.body).addClass('body-megamenu-active');
                    $this.layoutMegaMenu.addClass('layout-megamenu-active fadeInDown');
                }

                e.preventDefault();
            });

            $this.layoutMegaMenu.off('click').on('click', function (e) {
                $this.megaMenuClick = true;
            });

            $this.topbarMenuButton.off('click').on('click', function (e) {
                //TODO: Move to CSS
                $this.topbarUserMenu.css({top: '60px', right: '0', left: 'auto'});
                $this.topbarMenuClick = true;

                if ($this.topbarUserMenu.hasClass('usermenu-active')) {
                    $this.topbarUserMenu.removeClass('fadeInDown').addClass('fadeOutUp');

                    setTimeout(function () {
                        $this.topbarUserMenu.removeClass('usermenu-active fadeOutUp');
                    }, 250);
                }
                else {
                    $this.topbarUserMenu.addClass('usermenu-active fadeInDown');
                }

                e.preventDefault();
            });

            $this.topbarUserMenu.off('click').on('click', function (e) {
                $this.topbarMenuClick = true;
            });

            $this.menulinks.off('click').on('click', function (e) {
                var link = $(this),
                    item = link.parent(),
                    submenu = item.children('ul');

                if (item.hasClass('active-menuitem')) {
                    if (submenu.length) {
                        $this.removeMenuitem(item.attr('id'));
                        item.removeClass('active-menuitem');
                        submenu.slideUp();
                    }
                }
                else {
                    $this.addMenuitem(item.attr('id'));
                    $this.deactivateItems(item.siblings(), true);
                    $this.activate(item);
                }

                setTimeout(function () {
                    $this.nanoContainer.nanoScroller();
                }, 500);

                if (submenu.length) {
                    e.preventDefault();
                }
            });

            $this.sidebarProfileButton.off('click').on('click', function (e) {
                $this.sidebarUserMenu.slideToggle();

                setTimeout(function () {
                    $this.nanoContainer.nanoScroller();
                    $this.setInlineProfileState($this.sidebarUserMenu.get(0).offsetParent == null ? false : true);
                }, 500);
                e.preventDefault();
            });

            $this.usermenuLinks.on('click', function (e) {
                var link = $(this),
                    item = link.parent(),
                    submenu = link.next();

                $this.usermenuLinkClick = true;
                item.siblings('.menuitem-active').removeClass('menuitem-active').children('ul').slideUp();

                if (item.hasClass('menuitem-active')) {
                    item.removeClass('menuitem-active');
                    submenu.slideUp();
                }
                else {
                    item.addClass('menuitem-active');
                    submenu.slideDown();
                }

                if (submenu.length) {
                    e.preventDefault();
                }
            });

            $this.sidebar.off('click').on('click', function () {
                $this.sidebarMenuClick = true;
            });

            $(document.body).off('click').on('click', function () {
                if (!$this.topbarMenuClick && $this.topbarUserMenu.hasClass('usermenu-active')) {
                    $this.topbarUserMenu.removeClass('usermenu-active')
                }

                if (!$this.megaMenuClick && $this.layoutMegaMenu.hasClass('layout-megamenu-active')) {
                    $this.layoutMegaMenu.removeClass('layout-megamenu-active');
                    $(document.body).removeClass('body-megamenu-active');
                }

                if (!$this.rightSidebarClick && $this.rightSidebar.hasClass('layout-right-sidebar-active')) {
                    $this.rightSidebar.removeClass('layout-right-sidebar-active')
                }

                if (!$this.sidebarMenuClick && ($this.wrapper.hasClass('layout-wrapper-sidebar-mobile-active') || $this.wrapper.hasClass('layout-wrapper-overlay-sidebar-active'))) {
                    $this.wrapper.removeClass('layout-wrapper-sidebar-mobile-active layout-wrapper-overlay-sidebar-active');
                }

                $this.megaMenuClick = false;
                $this.topbarMenuClick = false;
                $this.rightSidebarClick = false;
                $this.sidebarMenuClick = false;
            });

            $this._initRightSidebar();
        });
    },

    activate: function (item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if (submenu.length) {
            submenu.slideDown();
        }
    },

    deactivate: function (item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');

        if (submenu.length) {
            submenu.hide();
        }
    },

    deactivateItems: function (items, animate) {
        var $this = this;

        for (var i = 0; i < items.length; i++) {
            var item = items.eq(i),
                submenu = item.children('ul');

            if (submenu.length) {
                if (item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    item.find('.ink').remove();

                    if (animate) {
                        submenu.slideUp('normal', function () {
                            $(this).parent().find('.active-menuitem').each(function () {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        submenu.hide();
                        item.find('.active-menuitem').each(function () {
                            $this.deactivate($(this));
                        });
                    }

                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function () {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function () {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if (item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },

    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },

    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },

    saveMenuState: function () {
        $.cookie('california_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },

    clearMenuState: function () {
        $.removeCookie('california_expandeditems', {path: '/'});
    },

    setInlineProfileState: function (expanded) {
        if (expanded)
            $.cookie('california_inlineprofile_expanded', "1", {path: '/'});
        else
            $.removeCookie('california_inlineprofile_expanded', {path: '/'});
    },

    restoreMenuState: function () {
        var menucookie = $.cookie('california_expandeditems');
        if (menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');

                    var submenu = menuitem.children('ul');
                    if (submenu.length) {
                        submenu.show();
                    }
                }
            }
        }

        var inlineProfileCookie = $.cookie('california_inlineprofile_expanded');
        if (inlineProfileCookie) {
            this.sidebarUserMenu.show();
        }
    },

    enableModal: function () {
        this.modal = this.wrapper.append('<div class="layout-mask"></div>').children('.layout-mask');
    },

    disableModal: function () {
        this.modal.remove();
    },

    isOverlay: function () {
        return this.wrapper.hasClass('layout-wrapper-overlay-sidebar');
    },

    isTablet: function () {
        var width = window.innerWidth;
        return width <= 1024 && width > 640;
    },

    isDesktop: function () {
        return window.innerWidth > 1024;
    },

    isMobile: function () {
        return window.innerWidth <= 640;
    },

    _initRightSidebar: function () {
        var $this = this;

        this.rightSidebarButton = $('#right-sidebar-button');
        this.rightSidebar = $('#layout-right-sidebar');

        this.rightSidebarButton.off('click').on('click', function (e) {
            $this.rightSidebar.toggleClass('layout-right-sidebar-active');
            $this.rightSidebarClick = true;
            e.preventDefault();
        });

        this.rightSidebar.off('click').on('click', function (e) {
            $this.rightSidebarClick = true;
            e.preventDefault();
        });
    },

    closeRightSidebarMenu: function () {
        if (this.rightSidebar) {
            this.rightSidebar.removeClass('right-sidebar-active');
        }
    }

});

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD (Register as an anonymous module)
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node/CommonJS
        module.exports = factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            // If we can't parse the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
            return config.json ? JSON.parse(s) : s;
        } catch (e) {
        }
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write

        if (arguments.length > 1 && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path ? '; path=' + options.path : '',
                options.domain ? '; domain=' + options.domain : '',
                options.secure ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {},
            // To prevent the for loop in the first place assign an empty array
            // in case there are no cookies at all. Also prevents odd result when
            // calling $.cookie().
            cookies = document.cookie ? document.cookie.split('; ') : [],
            i = 0,
            l = cookies.length;

        for (; i < l; i++) {
            var parts = cookies[i].split('='),
                name = decode(parts.shift()),
                cookie = parts.join('=');

            if (key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        // Must not alter options, thus extending a fresh object...
        $.cookie(key, '', $.extend({}, options, {expires: -1}));
        return !$.cookie(key);
    };

}));

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if (window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({

        enableModality: function () {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },

        syncWindowResize: function () {
        }
    });
}

if (PrimeFaces.widget.InputSwitch) {
    PrimeFaces.widget.InputSwitch = PrimeFaces.widget.InputSwitch.extend({

        init: function (cfg) {
            this._super(cfg);

            if (this.input.prop('checked')) {
                this.jq.addClass('ui-inputswitch-checked');
            }
        },

        toggle: function () {
            var $this = this;

            if (this.input.prop('checked')) {
                this.uncheck();
                setTimeout(function () {
                    $this.jq.removeClass('ui-inputswitch-checked');
                }, 100);
            }
            else {
                this.check();
                setTimeout(function () {
                    $this.jq.addClass('ui-inputswitch-checked');
                }, 100);
            }
        }
    });
}

/* Issue #2131 */
if (window['PrimeFaces'] && window['PrimeFaces'].widget.Schedule) {
    PrimeFaces.widget.Schedule = PrimeFaces.widget.Schedule.extend({

        setupEventSource: function () {
            var $this = this,
                offset = moment().utcOffset() * 60000;

            this.cfg.events = function (start, end, timezone, callback) {
                var options = {
                    source: $this.id,
                    process: $this.id,
                    update: $this.id,
                    formId: $this.cfg.formId,
                    params: [
                        {name: $this.id + '_start', value: start.valueOf() + offset},
                        {name: $this.id + '_end', value: end.valueOf() + offset}
                    ],
                    onsuccess: function (responseXML, status, xhr) {
                        PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                            widget: $this,
                            handle: function (content) {
                                callback($.parseJSON(content).events);
                            }
                        });

                        return true;
                    }
                };

                PrimeFaces.ajax.Request.handle(options);
            }
        }
    });
}
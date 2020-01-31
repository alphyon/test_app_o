module.exports = {
  mode: 'spa',
  /*
   ** Headers of the page
   */
  head: {
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || ''
      }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      {
        rel: 'stylesheet',
        type: 'text/css',
        href:
          'https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css'
      },
      {
        rel: 'stylesheet',
        type: 'text/css',
        href:
          'https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css'
      },
      {
        rel: 'stylesheet',
        type: 'text/css',
        href:
          'https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css'
      }
    ],
    script: [
      {
        src:
          'https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js',
        type: 'text/javascript'
      },
      {
        src: 'https://code.jquery.com/jquery-3.3.1.slim.min.js',
        type: 'text/javascript'
      },
      {
        src:
          'https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js',
        type: 'text/javascript'
      },
      {
        src: 'https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js',
        type: 'text/javascript'
      },
      {
        src:
          'https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js',
        type: 'text/javascript'
      }
      // {
      //   src: '/module/initdatatable.js',
      //   type: 'text/javascript'
      // }
    ]
  },
  /*
   ** Customize the progress-bar color
   */
  loading: { color: '#789774', throttle: 0 },
  /*
   ** Global CSS
   */

  css: [{ src: '~/assets/css/style.css', lang: 'css' }],
  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    '~/plugins/repository',
    '~/plugins/axios',
    '~/plugins/vue-swal',
    {
      src: '~/plugins/VueDraggable.js',
      ssr: false
    }
  ],
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module'
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://bootstrap-vue.js.org
    // 'bootstrap-vue/nuxt',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/auth',
    '@nuxtjs/proxy',
    '@nuxtjs/pwa',
    [
      'nuxt-fontawesome',
      {
        imports: [
          {
            set: '@fortawesome/free-solid-svg-icons',
            icons: ['fas']
          },
          {
            set: '@fortawesome/free-brands-svg-icons',
            icons: ['fab']
          }
        ]
      }
    ],
    [
      'nuxt-validate',
      {
        lang: 'es',
        nuxti18n: {
          locale: {
            'es': 'es'
          }
        }
      }
    ]
  ],

  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    baseURL: '/api/',
    // proxyHeaders: false,
    credentials: true
    // proxy: true,
  },
  proxy: {
    '/api/': {
      target: 'http://localhost:8080/api/v1/',
      pathRewrite: { '^/api/': '' }
    }
  },
  /*
   ** Build configuration
   */
  build: {
    /*
     ** You can extend webpack config here
     */
    extractCSS: true,
    extend(config, ctx) {},
    vendor: ['vue-swal']
  },

  auth: {
    strategies: {
      local: {
        endpoints: {
          login: {
            url: 'login',
            method: 'post',
            propertyName: 'token'
          },
          logout: false,
          user: { url: 'me', method: 'get', propertyName: 'data' }
        },
        tokenRequired: true,
        tokenType: 'Bearer'
      }
    },
    redirect: {
      login: '/login',
      logout: '/logout',
      callback: '/login',
      home: '/inicio'
    },
    // cookie: false,
    localStorage: false,
    token: {
      prefix: 'token'
    }
  },

  router: {
    middleware: ['auth']
  }
}

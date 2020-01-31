export default function({ $axios, store }) {
  $axios.onRequest((config) => {
    if (store.state.auth.tokenlocal) {
      config.headers.common['Authorization'] = store.state.auth.tokenlocal
    }
  })
}

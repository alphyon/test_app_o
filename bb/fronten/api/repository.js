export default ($axios) => (resource) => ({
  index(path) {
    return $axios.$get(`${resource}/${path}`)
  },

  show(path, id) {
    return $axios.$get(`${resource}/${path}/${id}`)
  },

  create(path, payload) {
    return $axios.$post(`${resource}/${path}`, payload)
  },

  update(path, id, payload) {
    return $axios.$put(`${resource}/${path}/${id}`, payload)
  },

  delete(path, id) {
    return $axios.$delete(`${resource}/${path}/${id}`)
  }
})

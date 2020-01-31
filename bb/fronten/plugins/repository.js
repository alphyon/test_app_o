import createRepository from '~/api/repository'
export default (ctx, inject) => {
    const repositoryWithAxios = createRepository(ctx.$axios)
    inject('categoryRepository', repositoryWithAxios('/category'))
    inject('channelRepository', repositoryWithAxios('/channel'))
    inject('manifoldRepository', repositoryWithAxios('/collector'))
    inject('interpretadoresRepository', repositoryWithAxios('/interpreters'))
    inject('usuariosRepository', repositoryWithAxios('/users'))
    inject('rolesRepository', repositoryWithAxios('/roles'))
    inject('permisosRepository', repositoryWithAxios('/permissions'))
    inject('bankCoreSimulator', repositoryWithAxios('/core'))
    inject('proxyTestRepository', repositoryWithAxios('/testproxy'))
    inject('testUrlRepository', repositoryWithAxios('/test')) 
    inject('sendMailRepository', repositoryWithAxios('/send')) 
    inject('orchestratorRepository', repositoryWithAxios('/orchestrator')) 
}
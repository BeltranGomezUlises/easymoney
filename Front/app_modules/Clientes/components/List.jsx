import React from 'react';
import { Button, Segment, Pagination, Table, Modal, Header, Image } from 'semantic-ui-react';
import * as utils from '../../../utils.js';
import EntityEdit from './EditForm.jsx';


export default class List extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            activeEntity: null,
            modalOpenEliminar: false,
            modalOpenEditar: false,
            activePage: 1,
            totalPages: 1,
            itemsPerPage: 5
        }
        this.closeModalEliminar = this.closeModalEliminar.bind(this);
        this.openModalEliminar = this.openModalEliminar.bind(this);
        this.closeModalEditar = this.closeModalEditar.bind(this);
        this.openModalEditar = this.openModalEditar.bind(this);
        this.eliminar = this.eliminar.bind(this);
    }

    openModalEliminar() {
        this.setState({ modalOpenEliminar: true });
    };

    closeModalEliminar() {
        this.setState({ modalOpenEliminar: false });
    };

    openModalEditar() {
        this.setState({ modalOpenEditar: true });
    };

    closeModalEditar() {
        this.setState({ modalOpenEditar: false });
    };

    componentWillReceiveProps(nextProps) {
        if (nextProps.collection !== this.props.collection) {
            let totalPages = Math.ceil(nextProps.collection.length / this.state.itemsPerPage);
            this.setState({
                activePage: 1,
                totalPages
            });
        }
    }

    eliminar() {
        this.setState({ loading: true });
        fetch(localStorage.getItem('url') + 'clientes', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('tokenSesion')
            },
            body: JSON.stringify({
                id: this.state.activeEntity.id
            })
        }).then((res) => res.json())
            .then((response) => {
                this.setState({ loading: false });
                this.closeModalEliminar();
                utils.evalResponse(response, () => {
                    this.props.load();
                });
            })
    };

    renderList() {
        let {activePage, itemsPerPage} = this.state;
        const limiteSuperior = activePage * itemsPerPage;
        let collection = this.props.collection.slice(limiteSuperior - itemsPerPage, limiteSuperior);
        return collection.map(p => {
            let imageRoute = '';
            if (p.imagen !== undefined) {
                imageRoute = localStorage.getItem('url') + 'utilerias/getFile/' + p.imagen;
            }
            return (
                <Table.Row key={p.id}>
                    <Table.Cell>
                        <Image src={imageRoute} size='small' centered bordered />
                    </Table.Cell>
                    <Table.Cell>{p.nombre}</Table.Cell>
                    <Table.Cell>{p.apodo}</Table.Cell>
                    <Table.Cell>{p.telefono}</Table.Cell>
                    <Table.Cell>{p.direccion}</Table.Cell>
                    <Table.Cell textAlign='center'>
                        <Button icon='edit' size='small' color='blue' onClick={() => {
                            this.setState({ activeEntity: p });
                            this.openModalEditar();
                        }} />
                        <Button icon='trash' size='small' color='red'
                            onClick={() => {
                                this.setState({ activeEntity: p });
                                this.openModalEliminar();
                            }} />
                    </Table.Cell>
                </Table.Row>
            )
        })
    }

    render() {
        if (this.props.collection == null) {
            return (
                <Segment style={{ 'height': '100px' }}>
                    Buscar clientes para mostrar...
                </Segment>
            )
        }
        if (this.props.collection.length == 0) {
            return (
                <Segment textAlign='center'>
                    <h3>Sin clientes para mostrar</h3>
                </Segment>
            )
        }
        return (
            <Segment>

                <Table>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Foto</Table.HeaderCell>
                            <Table.HeaderCell>Nombre</Table.HeaderCell>
                            <Table.HeaderCell>Apodo</Table.HeaderCell>
                            <Table.HeaderCell>Teléfono</Table.HeaderCell>
                            <Table.HeaderCell>Dirección</Table.HeaderCell>
                            <Table.HeaderCell />
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {this.renderList()}
                    </Table.Body>
                </Table>
                <Pagination
                    activePage={this.state.activePage}
                    totalPages={this.state.totalPages}
                    onPageChange={(e, { activePage }) => {
                        this.setState({ activePage });
                    }}
                />
                {/*Modal para eliminar*/}
                <Modal
                    onClose={this.closeModalEliminar}
                    onOpen={this.openModalEliminar}
                    open={this.state.modalOpenEliminar}>
                    <Header content='Eliminar cliente' textAlign='center' />
                    <Modal.Content>
                        <h4 align='center'>¿Está seguro de eliminar el cliente?</h4>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button loading={this.state.loading}
                            color='red'
                            onClick={this.state.loading ? null : this.eliminar}>
                            Eliminar
                        </Button>
                    </Modal.Actions>
                </Modal>
                {/*Modal para editar*/}
                <Modal
                    onClose={this.closeModalEditar}
                    onOpen={this.openModalEditar}
                    open={this.state.modalOpenEditar}>
                    <Header content='Editar cliente' textAlign='center' />
                    <Modal.Content>
                        <EntityEdit entity={this.state.activeEntity}
                            closeModal={this.closeModalEditar}
                            filter={this.props.filter} />
                    </Modal.Content>
                </Modal>

            </Segment>
        )
    }
}

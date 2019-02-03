import React from 'react';
import {
    Segment, Form, Divider,
    Button, Icon, Modal, Header
} from 'semantic-ui-react';

import * as utils from '../../../utils.js';
import EntityForm from './Form.jsx';

export default class Filter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            filter: {
                name: ''
            },
            filtering: false,
            openModal: false,
            data: []
        };
        this.filter = this.filter.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.load = this.load.bind(this);
    }

    componentDidMount() {
        this.load();
    }

    load() {
        fetch(localStorage.getItem('url') + "clientes", {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('tokenSesion')
            }
        }).then((res) => res.json())
            .then((r) => {
                this.setState({ filtering: false });
                utils.evalResponse(r, () => {
                    let { filter } = this.state;
                    filter.name = '';

                    let data = r.data.sort((item1, item2) => {
                        if (item1.nombre < item2.nombre)
                            return -1;
                        if (item1.nombre > item2.nombre)
                            return 1;
                        return 0;
                    });
                    
                    this.setState({ data, filter });
                    this.props.updateCollection(r.data);
                })
            });
    }

    openModal() {
        this.setState({ openModal: true })
    }

    closeModal() {
        this.setState({ openModal: false })
    }

    filter() {
        let { filter, data } = this.state;
        let value = filter.name.toLocaleLowerCase();
        let filteredData = [];

        if (value === '') {
            this.props.updateCollection(data);
        } else {
            data.forEach(c => {
                if (c.nombre.toLocaleLowerCase().includes(value)
                    || c.apodo.toLocaleLowerCase().includes(value)) {
                    filteredData.push(c);
                }
            });
            this.props.updateCollection(filteredData);
        }
    }

    render() {
        return (
            <Segment style={{ 'padding': '10px' }}>
                <Divider horizontal>Filtros de Búsqueda</Divider>
                <Form onSubmit={this.filter}>
                    <Form.Group>
                        <Form.Field>
                            <label>Nombre ó apodo de cliente</label>
                            <input type='text'
                                value={this.state.filter.name}
                                onChange={(evt) => {
                                    let { filter } = this.state;
                                    filter.name = evt.target.value;
                                    this.setState({ filter });
                                    if (filter.name == '') {
                                        this.filter();
                                    }
                                }}
                            />
                        </Form.Field>
                    </Form.Group>
                    <Button primary animated
                        loading={this.state.filtering}
                        type={this.state.filtering ? 'button' : 'submit'}>
                        <Button.Content hidden>Buscar</Button.Content>
                        <Button.Content visible>
                            <Icon name='filter' />
                        </Button.Content>
                    </Button>
                    <Button color='green'
                        animated type='button' onClick={this.openModal}>
                        <Button.Content hidden>Nuevo</Button.Content>
                        <Button.Content visible>
                            <Icon name='plus' />
                        </Button.Content>
                    </Button>
                </Form>
                <Modal
                    onClose={this.closeModal}
                    onOpen={this.openModal}
                    open={this.state.openModal}>
                    <Header content='Agregar un cliente' textAlign='center' />
                    <Modal.Content>
                        <EntityForm
                            filter={this.filter}
                            closeModal={this.closeModal}
                            load={this.load}
                            clientes={this.state.data} />
                    </Modal.Content>
                </Modal>
            </Segment>
        );
    }

}
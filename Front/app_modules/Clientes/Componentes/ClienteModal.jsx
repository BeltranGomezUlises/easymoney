import React, { Component } from 'react'
import { Button, Modal, Input } from 'semantic-ui-react'

class ClienteModal extends Component {
  render() {
    this.state = { open: false }
    show = () => this.setState({ open: true });
    close = () => this.setState({ open: false });

    const { open, size } = this.state;

    return (
      <div>
        <Modal size='tiny' open={open} onClose={this.close}>
          <Modal.Header>
            Delete Your Account
          </Modal.Header>
          <Modal.Content>
            <p>Are you sure you want to delete your account</p>
          </Modal.Content>
          <Modal.Actions>
            <Button negative>
              No
            </Button>
            <Button positive icon='checkmark' labelPosition='right' content='Yes' />
          </Modal.Actions>
        </Modal>
      </div>
    )
  }
}

export default ClienteModal

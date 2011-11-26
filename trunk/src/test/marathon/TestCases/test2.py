#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.6.0_25")
    if window('WServer'):
        click('...')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be running in maintenance mode to change web root')
            click('OptionPane.button')
        close()

        click('Set')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be started in maintenance mode to change port')
            click('OptionPane.button')
        close()

        click('Start')
        click('...')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be running in maintenance mode to change web root')
            click('OptionPane.button')
        close()

        click('Set')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be started in maintenance mode to change port')
            click('OptionPane.button')
        close()

        click('Stop')
        click('...')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be running in maintenance mode to change web root')
            click('OptionPane.button')
        close()

        click('Set')

        if window('Message'):
            assert_p('OptionPane.label', 'Text', 'Server must be started in maintenance mode to change port')
            click('OptionPane.button')
        close()
    close()

